
package acme.features.sponsor.nonCommercialBanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.banners.NonCommercialBanner;
import acme.entities.roles.Sponsor;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractDeleteService;

@Service
public class SponsorNonCommercialBannerDeleteService implements AbstractDeleteService<Sponsor, NonCommercialBanner> {

	//Internal state

	@Autowired
	SponsorNonCommercialBannerRepository repository;


	//AbstractUpdateService<Sponsor, NonCommercialBanner> interface

	@Override
	public boolean authorise(final Request<NonCommercialBanner> request) {
		assert request != null;

		//Solo el sponsor que creó el banner puede borrarlo
		Integer bannerId = request.getModel().getInteger("id");
		Integer sponsorId = this.repository.findOneNonCommercialBannerById(bannerId).getSponsor().getId();
		boolean thisSponsor = sponsorId.equals(request.getPrincipal().getActiveRoleId());

		return thisSponsor;
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
	public NonCommercialBanner findOne(final Request<NonCommercialBanner> request) {
		assert request != null;
		NonCommercialBanner result;
		int id;
		id = request.getModel().getInteger("id");
		result = this.repository.findOneNonCommercialBannerById(id);

		return result;
	}

	@Override
	public void validate(final Request<NonCommercialBanner> request, final NonCommercialBanner entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
	}

	@Override
	public void delete(final Request<NonCommercialBanner> request, final NonCommercialBanner entity) {
		assert request != null;
		assert entity != null;

		this.repository.delete(entity);

	}
}
