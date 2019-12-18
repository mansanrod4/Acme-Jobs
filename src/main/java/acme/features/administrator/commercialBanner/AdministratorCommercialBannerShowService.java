
package acme.features.administrator.commercialBanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.banners.CommercialBanner;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Administrator;
import acme.framework.services.AbstractShowService;

@Service
public class AdministratorCommercialBannerShowService implements AbstractShowService<Administrator, CommercialBanner> {

	//Internal State

	@Autowired
	private AdministratorCommercialBannerRepository repository;


	//AbstractListService<Administrator, CommercialBanner> interface

	@Override
	public boolean authorise(final Request<CommercialBanner> request) {
		assert request != null;

		return true;
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
	public void unbind(final Request<CommercialBanner> request, final CommercialBanner entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		model.setAttribute("canUpdate", false);

		request.unbind(entity, model, "picture", "slogan", "targetURL", "creditCardNumber", "marca", "creditCardHolder", "expirationDate", "cvv");
	}

}
