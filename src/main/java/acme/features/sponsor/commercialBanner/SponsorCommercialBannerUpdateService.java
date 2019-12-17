
package acme.features.sponsor.commercialBanner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.components.SpamFilter;
import acme.entities.banners.CommercialBanner;
import acme.entities.roles.Sponsor;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractUpdateService;

@Service
public class SponsorCommercialBannerUpdateService implements AbstractUpdateService<Sponsor, CommercialBanner> {

	//Internal state

	@Autowired
	SponsorCommercialBannerRepository repository;


	//AbstractUpdateService<Sponsor, CommercialBanner> interface

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

		request.unbind(entity, model, "picture", "slogan", "targetURL", "creditCardNumber", "expirationDate", "cvv");
	}

	@Override
	public CommercialBanner findOne(final Request<CommercialBanner> request) {
		assert request != null;
		CommercialBanner result;
		int id;
		id = request.getModel().getInteger("id");
		result = this.repository.findOneCommercialBannerById(id);

		return result;
	}

	@Override
	public void validate(final Request<CommercialBanner> request, final CommercialBanner entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		//Validaciones

		boolean hasCreditCard, isCVVOk, isExpirationDateOk, isSpam;

		//Sponsor debe tener tarjeta de crédito registrada (a sponsor cannot create any commercial banners unless he or she has registered a credit card)
		Integer sponsorId = request.getPrincipal().getActiveRoleId();
		hasCreditCard = !this.repository.findSponsorById(sponsorId).getCreditCardNumber().isEmpty();
		errors.state(request, hasCreditCard, "creditCardNumber", "sponsor.commercial-banner.error.hasNotCreditCard");

		//CreditCardNumber cvv pattern
		Pattern p1 = Pattern.compile("^\\d{3}$");
		Matcher m1 = p1.matcher(request.getModel().getString("cvv"));
		isCVVOk = m1.matches();
		errors.state(request, isCVVOk, "cvv", "sponsor.commercial-banner.error.cvv");

		//CreditCardNumber expirationDate pattern
		Pattern p2 = Pattern.compile("^(1[0-2]|0[1-9]|\\d)\\/(\\d{2})$");
		Matcher m2 = p2.matcher(request.getModel().getString("expirationDate"));
		isExpirationDateOk = m2.matches();
		errors.state(request, isExpirationDateOk, "expirationDate", "sponsor.commercial-banner.error.expirationDate");

		//ExpirationDate correcto
		if (!errors.hasErrors("expirationDate")) {
			String[] exdate = entity.getExpirationDate().split("/");
			try {
				Date date = new SimpleDateFormat("dd/MM/yy").parse("01/" + exdate[0] + "/" + exdate[1]);
				Date today = new Date();
				errors.state(request, date.after(today), "expirationDate", "sponsor.commercial-banner.error.creditCard");

			} catch (ParseException e) {
				errors.state(request, false, "expirationDate", "sponsor.commercial-banner.error.creditCard");
			}
		}

		//SPAMTIME
		Double threshold = this.repository.findSysconfig().getThreshold();
		String spamWords = this.repository.findSysconfig().getSpamwords();

		//Spam - picture
		if (!errors.hasErrors("picture")) {
			isSpam = SpamFilter.spamFilterUrl(request.getModel().getString("picture"), spamWords, threshold);
			errors.state(request, !isSpam, "picture", "sponsor.comercial-banner.error.isSpam");
		}

		//Spam - slogan
		if (!errors.hasErrors("slogan")) {
			isSpam = SpamFilter.spamFilter(request.getModel().getString("slogan"), spamWords, threshold);
			errors.state(request, !isSpam, "slogan", "sponsor.comercial-banner.error.isSpam");
		}

		//Spam - targetUrl
		if (!errors.hasErrors("targetURL")) {
			isSpam = SpamFilter.spamFilterUrl(request.getModel().getString("targetURL"), spamWords, threshold);
			errors.state(request, !isSpam, "targetURL", "sponsor.comercial-banner.error.isSpam");
		}

	}

	@Override
	public void update(final Request<CommercialBanner> request, final CommercialBanner entity) {
		assert request != null;
		assert entity != null;

		this.repository.save(entity);

	}
}
