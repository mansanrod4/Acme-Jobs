
package acme.features.sponsor.nonCommercialBanner;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.banners.NonCommercialBanner;
import acme.entities.roles.Sponsor;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractListService;

@Service
public class SponsorNonCommercialBannerListService implements AbstractListService<Sponsor, NonCommercialBanner> {

	@Autowired
	SponsorNonCommercialBannerRepository repository;


	//AbstractListService<Sponsor, Banner> interface

	@Override
	public boolean authorise(final Request<NonCommercialBanner> request) {
		assert request != null;

		return true;
	}

	@Override
	public void unbind(final Request<NonCommercialBanner> request, final NonCommercialBanner entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "picture", "slogan", "targetURL");
	}

	@Override
	public Collection<NonCommercialBanner> findMany(final Request<NonCommercialBanner> request) {
		assert request != null;

		Collection<NonCommercialBanner> result;
		Principal principal;

		principal = request.getPrincipal();
		result = this.repository.findManyByNonCommercialBannerId(principal.getActiveRoleId());

		return result;
	}

}
