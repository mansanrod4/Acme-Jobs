
package acme.features.administrator.nonCommercialBanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.banners.NonCommercialBanner;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Administrator;
import acme.framework.services.AbstractCreateService;

@Service
public class AdministratorNonCommercialBannerCreateService implements AbstractCreateService<Administrator, NonCommercialBanner> {

	//Internal state

	@Autowired
	AdministratorNonCommercialBannerRepository repository;


	//AbstractUpdateService<Administrator, NonCommercialBanner> interface

	@Override
	public boolean authorise(final Request<NonCommercialBanner> request) {
		assert request != null;
		return true;
	}

	@Override
	public void bind(final Request<NonCommercialBanner> request, final NonCommercialBanner entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors);

	}

	@Override
	public void unbind(final Request<NonCommercialBanner> request, final NonCommercialBanner entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "picture", "slogan", "targetURL", "jingle");
	}

	@Override
	public NonCommercialBanner instantiate(final Request<NonCommercialBanner> request) {

		NonCommercialBanner result;

		result = new NonCommercialBanner();

		return result;
	}

	@Override
	public void validate(final Request<NonCommercialBanner> request, final NonCommercialBanner entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
	}

	@Override
	public void create(final Request<NonCommercialBanner> request, final NonCommercialBanner entity) {
		assert request != null;
		assert entity != null;

		this.repository.save(entity);

	}
}
