/*
 * AuthenticatedConsumerCreateService.java
 *
 * Copyright (c) 2019 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.authenticated.sponsor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.roles.Sponsor;
import acme.framework.components.Errors;
import acme.framework.components.HttpMethod;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.components.Response;
import acme.framework.entities.Authenticated;
import acme.framework.entities.Principal;
import acme.framework.entities.UserAccount;
import acme.framework.helpers.PrincipalHelper;
import acme.framework.services.AbstractCreateService;

@Service
public class AuthenticatedSponsorCreateService implements AbstractCreateService<Authenticated, Sponsor> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuthenticatedSponsorRepository repository;

	// AbstractCreateService<Authenticated, Sponsor> ---------------------------


	@Override
	public boolean authorise(final Request<Sponsor> request) {
		assert request != null;

		return true;
	}

	@Override
	public void validate(final Request<Sponsor> request, final Sponsor entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		boolean isCreditCardOk, isCVVOk, isExpirationDateOk, thereIsNotCreditCard;

		//CreditCardNumber
		if (!request.getModel().getString("creditCardNumber").equals("")) {
			String regex = "^(?:(?<visa>4[0-9]{12}(?:[0-9]{3})?)|" + "(?<mastercard>5[1-5][0-9]{14})|" + "(?<discover>6(?:011|5[0-9]{2})[0-9]{12})|" + "(?<amex>3[47][0-9]{13})|" + "(?<diners>3(?:0[0-5]|[68][0-9])?[0-9]{11})|"
				+ "(?<jcb>(?:2131|1800|35[0-9]{3})[0-9]{11}))$";
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(request.getModel().getString("creditCardNumber"));
			isCreditCardOk = m.matches();
			errors.state(request, isCreditCardOk, "creditCardNumber", "authenticated.sponsor.error.creditcard");
		}
		//CreditCardNumber cvv
		if (!request.getModel().getString("expirationDate").equals("")) {
			Pattern p = Pattern.compile("^(1[0-2]|0[1-9]|\\d)\\/(\\d{2})$");
			Matcher m = p.matcher(request.getModel().getString("expirationDate"));
			isExpirationDateOk = m.matches();
			errors.state(request, isExpirationDateOk, "expirationDate", "authenticated.sponsor.error.expirationDate");

		}
		//CreditCardNumber expirationDate
		if (!request.getModel().getString("cvv").equals("")) {
			Pattern p = Pattern.compile("^\\d{3}$");
			Matcher m = p.matcher(request.getModel().getString("cvv"));
			isCVVOk = m.matches();
			errors.state(request, isCVVOk, "cvv", "authenticated.sponsor.error.cvv");
		}

		//No puede haber cvv o expiration date si no hay CreditCardNumber
		boolean creditCard, cvv, date;
		creditCard = request.getModel().getString("creditCardNumber").equals("");
		cvv = request.getModel().getString("cvv").equals("");
		date = request.getModel().getString("expirationDate").equals("");

		thereIsNotCreditCard = creditCard && !cvv || creditCard && !date;

		errors.state(request, !thereIsNotCreditCard, "creditCardNumber", "authenticated.sponsor.error.noCreditCard");

	}

	@Override
	public void bind(final Request<Sponsor> request, final Sponsor entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors);
	}

	@Override
	public void unbind(final Request<Sponsor> request, final Sponsor entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "orgName", "creditCardNumber", "expirationDate", "cvv");
	}

	@Override
	public Sponsor instantiate(final Request<Sponsor> request) {
		assert request != null;

		Sponsor result;
		Principal principal;
		int userAccountId;
		UserAccount userAccount;

		principal = request.getPrincipal();
		userAccountId = principal.getAccountId();
		userAccount = this.repository.findOneUserAccountById(userAccountId);

		result = new Sponsor();
		result.setUserAccount(userAccount);

		return result;
	}

	@Override
	public void create(final Request<Sponsor> request, final Sponsor entity) {
		assert request != null;
		assert entity != null;

		this.repository.save(entity);
	}

	@Override
	public void onSuccess(final Request<Sponsor> request, final Response<Sponsor> response) {
		assert request != null;
		assert response != null;

		if (request.isMethod(HttpMethod.POST)) {
			PrincipalHelper.handleUpdate();
		}
	}

}
