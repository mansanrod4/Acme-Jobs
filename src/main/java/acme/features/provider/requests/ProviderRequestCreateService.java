
package acme.features.provider.requests;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.requests.Request;
import acme.entities.roles.Provider;
import acme.framework.components.Errors;
import acme.framework.components.HttpMethod;
import acme.framework.components.Model;
import acme.framework.services.AbstractCreateService;

@Service
public class ProviderRequestCreateService implements AbstractCreateService<Provider, Request> {

	//Internal state

	@Autowired
	ProviderRequestRepository repository;


	//AbstractUpdateService<Administrator, Announcement> interface

	@Override
	public boolean authorise(final acme.framework.components.Request<Request> request) {
		assert request != null;
		return true;
	}

	@Override
	public void bind(final acme.framework.components.Request<Request> request, final Request entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		Date moment;
		moment = new Date(System.currentTimeMillis() - 1);
		entity.setCreationMoment(moment);

		request.bind(entity, errors, "creationMoment");

	}

	@Override
	public void unbind(final acme.framework.components.Request<Request> request, final Request entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "ticker", "title", "creationMoment", "deadLine", "text", "reward");

		if (request.isMethod(HttpMethod.GET)) {
			model.setAttribute("accept", "false");
		} else {
			request.transfer(model, "accept");
		}

	}

	@Override
	public Request instantiate(final acme.framework.components.Request<Request> request) {

		Request result;

		result = new Request();

		return result;
	}

	@Override
	public void validate(final acme.framework.components.Request<Request> request, final Request entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		//Validaciones

		boolean isAccepted, isDuplicated, isEuroZone = false, isOneWeekLater, isPatternOk;

		//Moneda EUR

		if (!errors.hasErrors("reward")) {
			String eur2 = "€", eur = "EUR", currency = request.getModel().getAttribute("reward").toString();
			if (currency.contains(eur) || currency.contains(eur2)) {
				isEuroZone = true;
			}
			errors.state(request, isEuroZone, "reward", "provider.request.error.money-no-euro");
		}

		//Checkbox
		isAccepted = request.getModel().getBoolean("accept");
		errors.state(request, isAccepted, "accept", "provider.request.error.must-accept");

		//Ticker duplicado
		isDuplicated = this.repository.findOneRequestByTicker(entity.getTicker()) != null;
		errors.state(request, !isDuplicated, "ticker", "provider.request.error.duplicated");

		//Ticker pattern internacional
		Pattern p = Pattern.compile("R\\p{Lu}{4}-\\d{5}");
		Matcher m = p.matcher(entity.getTicker());
		isPatternOk = m.matches();
		errors.state(request, isPatternOk, "ticker", "provider.request.error.ticker");

		//DEADLINE MAYOR A UNA SEMANA DESDE AHORA
		if (!errors.hasErrors("deadLine")) {									//Si no hay errores:

			LocalDateTime now = LocalDateTime.now();							//Obtenemos la fecha actual
			LocalDateTime nowplus7 = now.plus(7, ChronoUnit.DAYS);				//Le sumamos una semana
			Date date = Timestamp.valueOf(nowplus7);
			Date date1 = request.getModel().getDate("deadLine");				//Obtenemos la fecha insertada

			isOneWeekLater = date1.after(date);									//Comparamos fechas
			errors.state(request, isOneWeekLater, "deadLine", "consumer.offer.error.deadline");
		}
	}

	@Override
	public void create(final acme.framework.components.Request<Request> request, final Request entity) {

		Date moment;

		moment = new Date(System.currentTimeMillis() - 1);
		entity.setCreationMoment(moment);
		this.repository.save(entity);

	}
}
