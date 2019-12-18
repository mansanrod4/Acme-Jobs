
package acme.features.sponsor.commercialBanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.banners.CommercialBanner;
import acme.entities.banners.CreditCardBrand;
import acme.entities.roles.Sponsor;
import acme.framework.components.HttpMethod;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractShowService;

@Service
public class SponsorCommercialBannerShowService implements AbstractShowService<Sponsor, CommercialBanner> {

	@Autowired
	SponsorCommercialBannerRepository repository;


	//AbstractListService<Sponsor, Banner> interface

	@Override
	public boolean authorise(final Request<CommercialBanner> request) {
		assert request != null;

		boolean result;
		int bannerId;
		CommercialBanner banner;
		Sponsor sponsor;
		Principal principal;

		bannerId = request.getModel().getInteger("id");
		banner = this.repository.findOneCommercialBannerById(bannerId);
		sponsor = banner.getSponsor();
		principal = request.getPrincipal();
		result = sponsor.getUserAccount().getId() == principal.getAccountId();

		return result;
	}

	@Override
	public void unbind(final Request<CommercialBanner> request, final CommercialBanner entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		model.setAttribute("readOnly", true);

		if (request.isMethod(HttpMethod.GET)) {
			if (entity.getMarca().equals(CreditCardBrand.AMERICAN_EXPRESS)) {
				model.setAttribute("marcaString", "American Express");
			} else if (entity.getMarca().equals(CreditCardBrand.DINERS_CLUB)) {
				model.setAttribute("marcaString", "Diners Club");
			} else if (entity.getMarca().equals(CreditCardBrand.MASTERCARD)) {
				model.setAttribute("marcaString", "MasterCard");
			} else if (entity.getMarca().equals(CreditCardBrand.VISA)) {
				model.setAttribute("marcaString", "VISA");
			}

		} else {
			request.transfer(model, "marcaString");
		}

		request.unbind(entity, model, "picture", "slogan", "targetURL", "marca", "creditCardHolder", "creditCardNumber", "expirationDate", "cvv");
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

}
