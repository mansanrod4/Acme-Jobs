
package acme.features.consumer.offer;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.offers.Offer;
import acme.entities.roles.Consumer;
import acme.framework.components.Errors;
import acme.framework.components.HttpMethod;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractCreateService;

@Service
public class ConsumerOfferCreateService implements AbstractCreateService<Consumer, Offer> {

	//Internal state

	@Autowired
	ConsumerOfferRepository repository;


	@Override
	public boolean authorise(final Request<Offer> request) {
		assert request != null;
		return true;
	}

	@Override
	public void bind(final Request<Offer> request, final Offer entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		Date moment;
		moment = new Date(System.currentTimeMillis() - 1);
		entity.setMoment(moment);

		request.bind(entity, errors, "moment");

	}

	@Override
	public void unbind(final Request<Offer> request, final Offer entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "title", "moment", "deadLine", "text", "money", "ticker");

		if (request.isMethod(HttpMethod.GET)) {
			model.setAttribute("accept", "false");
		} else {
			request.transfer(model, "accept");
		}
	}

	@Override
	public Offer instantiate(final Request<Offer> request) {
		Offer result;
		result = new Offer();

		return result;
	}

	@Override
	public void validate(final Request<Offer> request, final Offer entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		//Validaciones

		boolean isAccepted, isDuplicated, isEuroZone = false, isOneWeekLater, isPatternOk;

		//Moneda EUR
		if (!errors.hasErrors("money")) {
			String eur2 = "€", eur = "EUR", currency = request.getModel().getAttribute("money").toString();
			if (currency.contains(eur) || currency.contains(eur2)) {
				isEuroZone = true;
			}
			errors.state(request, isEuroZone, "money", "consumer.offer.error.money-no-euro");
		}

		//Checkbox
		isAccepted = request.getModel().getBoolean("accept");
		errors.state(request, isAccepted, "accept", "consumer.offer.error.must-accept");

		//Ticker duplicado
		isDuplicated = this.repository.findOneOfferByTicker(entity.getTicker()) != null;
		errors.state(request, !isDuplicated, "ticker", "consumer.offer.error.duplicated");

		//Ticker pattern internacional
		Pattern p = Pattern.compile("O\\p{Lu}{4}-\\d{5}");
		Matcher m = p.matcher(entity.getTicker());
		isPatternOk = m.matches();
		errors.state(request, isPatternOk, "ticker", "consumer.offer.error.ticker");

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
	public void create(final Request<Offer> request, final Offer entity) {
		assert request != null;
		assert entity != null;

		Date moment;
		moment = new Date(System.currentTimeMillis() - 1);
		entity.setMoment(moment);
		this.repository.save(entity);

	}
}
