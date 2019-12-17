
package acme.features.sponsor.nonCommercialBanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.components.SpamFilter;
import acme.entities.banners.NonCommercialBanner;
import acme.entities.roles.Sponsor;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractCreateService;

@Service
public class SponsorNonCommercialBannerCreateService implements AbstractCreateService<Sponsor, NonCommercialBanner> {

	//Internal state

	@Autowired
	SponsorNonCommercialBannerRepository repository;


	//AbstractUpdateService<Sponsor, NonCommercialBanner> interface

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

		//Validaciones

		boolean isSpam;

		//SPAMTIME
		Double threshold = this.repository.findSysconfig().getThreshold();
		String spamWords = this.repository.findSysconfig().getSpamwords();

		//Spam - picture
		if (!errors.hasErrors("picture")) {
			isSpam = SpamFilter.spamFilterUrl(request.getModel().getString("picture"), spamWords, threshold);
			errors.state(request, !isSpam, "picture", "sponsor.non-commercial-banner.error.isSpam");
		}

		//Spam - slogan
		if (!errors.hasErrors("slogan")) {
			isSpam = SpamFilter.spamFilter(request.getModel().getString("slogan"), spamWords, threshold);
			errors.state(request, !isSpam, "slogan", "sponsor.non-commercial-banner.error.isSpam");
		}

		//Spam - targetUrl
		if (!errors.hasErrors("targetURL")) {
			isSpam = SpamFilter.spamFilterUrl(request.getModel().getString("targetURL"), spamWords, threshold);
			errors.state(request, !isSpam, "targetURL", "sponsor.non-commercial-banner.error.isSpam");
		}

		//Spam - jingle
		if (!errors.hasErrors("jingle")) {
			isSpam = SpamFilter.spamFilterUrl(request.getModel().getString("jingle"), spamWords, threshold);
			errors.state(request, !isSpam, "jingle", "sponsor.non-commercial-banner.error.isSpam");
		}

	}

	@Override
	public void create(final Request<NonCommercialBanner> request, final NonCommercialBanner entity) {
		assert request != null;
		assert entity != null;

		Integer sponsorId = request.getPrincipal().getActiveRoleId();
		Sponsor sponsor = this.repository.findSponsorById(sponsorId);
		entity.setSponsor(sponsor);

		this.repository.save(entity);
	}
}
