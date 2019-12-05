/*
 * AnonymousUserAccountCreateService.java
 *
 * Copyright (c) 2019 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.anonymous.userAccount;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.framework.components.Errors;
import acme.framework.components.HttpMethod;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Anonymous;
import acme.framework.entities.Authenticated;
import acme.framework.entities.UserAccount;
import acme.framework.entities.UserRole;
import acme.framework.services.AbstractCreateService;

@Service
public class AnonymousUserAccountCreateService implements AbstractCreateService<Anonymous, UserAccount> {

	// Internal state ---------------------------------------------------------

	@Autowired
	AnonymousUserAccountRepository repository;


	@Override
	public boolean authorise(final Request<UserAccount> request) {
		assert request != null;

		return true;
	}

	@Override
	public void bind(final Request<UserAccount> request, final UserAccount entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors);
	}

	@Override
	public void unbind(final Request<UserAccount> request, final UserAccount entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "username", "identity.name", "identity.surname", "identity.email");

		if (request.isMethod(HttpMethod.GET)) {
			model.setAttribute("password", "");
			model.setAttribute("confirmation", "");
			model.setAttribute("accept", "false");
		} else {
			request.transfer(model, "password", "confirmation", "accept");
		}
	}

	@Override
	public UserAccount instantiate(final Request<UserAccount> request) {
		assert request != null;

		UserAccount result;
		Authenticated defaultRole;

		result = new UserAccount();
		result.setEnabled(true);
		defaultRole = new Authenticated();
		result.addRole(defaultRole);
		defaultRole.setUserAccount(result);

		return result;
	}

	@Override
	public void validate(final Request<UserAccount> request, final UserAccount entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		boolean isDuplicated, isAccepted, isMatching;
		String password, confirmation;
		int passwordLength;

		isDuplicated = this.repository.findOneUserAccountByUsername(entity.getUsername()) != null;
		errors.state(request, !isDuplicated, "username", "anonymous.user-account.error.duplicated");

		passwordLength = request.getModel().getString("password").length();
		errors.state(request, passwordLength >= 5 && passwordLength <= 60, "password", "acme.validation.length", 6, 60);

		isAccepted = request.getModel().getBoolean("accept");
		errors.state(request, isAccepted, "accept", "anonymous.user-account.error.must-accept");

		password = request.getModel().getString("password");
		confirmation = request.getModel().getString("confirmation");
		isMatching = password.equals(confirmation);
		errors.state(request, isMatching, "confirmation", "anonymous.user-account.error.confirmation-no-match");
	}

	@Override
	public void create(final Request<UserAccount> request, final UserAccount entity) {
		assert request != null;
		assert entity != null;

		this.repository.save(entity);
		for (UserRole role : entity.getRoles()) {
			this.repository.save(role);
		}
	}

}
