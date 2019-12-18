
package acme.features.administrator.commercialBanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.banners.CommercialBanner;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Administrator;
import acme.framework.services.AbstractDeleteService;

@Service
public class AdministratorCommercialBannerDeleteService implements AbstractDeleteService<Administrator, CommercialBanner> {

	//Internal state

	@Autowired
	AdministratorCommercialBannerRepository repository;


	//AbstractUpdateService<Administrator, Investor> interface

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

		request.unbind(entity, model, "picture", "slogan", "targetURL", "creditCardNumber", "marca", "creditCardHolder", "expirationDate", "cvv");
	}

	@Override
	public CommercialBanner findOne(final Request<CommercialBanner> request) {
		assert request != null;
		CommercialBanner result;
		int id;
		id = request.getModel().getInteger("id");
		result = this.repository.findOneById(id);

		return result;
	}

	@Override
	public void validate(final Request<CommercialBanner> request, final CommercialBanner entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
	}

	@Override
	public void delete(final Request<CommercialBanner> request, final CommercialBanner entity) {
		assert request != null;
		assert entity != null;

		this.repository.delete(entity);

	}
}
