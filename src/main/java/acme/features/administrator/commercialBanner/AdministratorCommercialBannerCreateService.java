
package acme.features.administrator.commercialBanner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.banners.CommercialBanner;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Administrator;
import acme.framework.services.AbstractCreateService;

@Service
public class AdministratorCommercialBannerCreateService implements AbstractCreateService<Administrator, CommercialBanner> {

	//Internal state

	@Autowired
	AdministratorCommercialBannerRepository repository;


	//AbstractUpdateService<Administrator, CommercialBanner> interface

	@Override
	public boolean authorise(final Request<CommercialBanner> request) {
		assert request != null;
		return true;
	}

	@Override
	public void bind(final Request<CommercialBanner> request, final CommercialBanner entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors);
	}

	@Override
	public void unbind(final Request<CommercialBanner> request, final CommercialBanner entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		model.setAttribute("canUpdate", true);

		request.unbind(entity, model, "picture", "slogan", "targetURL", "creditCardNumber", "marca", "creditCardHolder", "expirationDate", "cvv");
	}

	@Override
	public CommercialBanner instantiate(final Request<CommercialBanner> request) {

		CommercialBanner result;

		result = new CommercialBanner();

		return result;
	}

	@Override
	public void validate(final Request<CommercialBanner> request, final CommercialBanner entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		if (!errors.hasErrors()) {
			String[] exdate = entity.getExpirationDate().split("/");
			try {
				Date date = new SimpleDateFormat("dd/MM/yy").parse("01/" + exdate[0] + "/" + exdate[1]);
				Date today = new Date();
				errors.state(request, date.after(today), "expirationDate", "administrator.comercial-banner.error.creditCard");

			} catch (ParseException e) {
				errors.state(request, false, "expirationDate", "administrator.comercial-banner.error.creditCard");
			}
		}
	}

	@Override
	public void create(final Request<CommercialBanner> request, final CommercialBanner entity) {
		assert request != null;
		assert entity != null;

		this.repository.save(entity);
	}
}
