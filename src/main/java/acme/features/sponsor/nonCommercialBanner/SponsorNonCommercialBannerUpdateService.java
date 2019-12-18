
package acme.features.sponsor.nonCommercialBanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.components.SpamFilter;
import acme.entities.banners.NonCommercialBanner;
import acme.entities.roles.Sponsor;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractUpdateService;

@Service
public class SponsorNonCommercialBannerUpdateService implements AbstractUpdateService<Sponsor, NonCommercialBanner> {

	//Internal state

	@Autowired
	SponsorNonCommercialBannerRepository repository;


	//AbstractUpdateService<Sponsor, NonCommercialBanner> interface

	@Override
	public boolean authorise(final Request<NonCommercialBanner> request) {
		assert request != null;

		//Solo el sponsor que creó el banner puede editarlo
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
	public void update(final Request<NonCommercialBanner> request, final NonCommercialBanner entity) {
		assert request != null;
		assert entity != null;

		this.repository.save(entity);

	}
}
