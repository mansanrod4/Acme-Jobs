/*
 * AuthenticatedUserAccountUpdateService.java
 *
 * Copyright (c) 2019 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.authenticated.userAccount;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.framework.components.Errors;
import acme.framework.components.HttpMethod;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.components.Response;
import acme.framework.entities.Authenticated;
import acme.framework.entities.Principal;
import acme.framework.entities.UserAccount;
import acme.framework.entities.UserRole;
import acme.framework.helpers.PrincipalHelper;
import acme.framework.services.AbstractUpdateService;

@Service
public class AuthenticatedUserAccountUpdateService implements AbstractUpdateService<Authenticated, UserAccount> {

	// Internal state ---------------------------------------------------------

	@Autowired
	AuthenticatedUserAccountRepository repository;


	// AbstractUpdateService<Authenticated, UserAccount> interface ------------

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

		String password;

		request.bind(entity, errors, "username", "password");
		password = request.getModel().getString("password");
		if (!password.equals("[MASKED-PASWORD]")) {
			entity.setPassword(password);
		}
	}

	@Override
	public void unbind(final Request<UserAccount> request, final UserAccount entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "username", "identity.name", "identity.surname", "identity.email");

		if (request.isMethod(HttpMethod.POST)) {
			request.transfer(model, "password");
			request.transfer(model, "confirmation");
		} else {
			request.unbind(entity, model, "password");
			model.setAttribute("password", "[MASKED-PASWORD]");
			model.setAttribute("confirmation", "[MASKED-PASWORD]");
		}
	}

	@Override
	public UserAccount findOne(final Request<UserAccount> request) {
		assert request != null;

		UserAccount result;
		Principal principal;

		principal = request.getPrincipal();
		result = this.repository.findOneUserAccountById(principal.getAccountId());
		result.getRoles().size();

		return result;
	}

	@Override
	public void validate(final Request<UserAccount> request, final UserAccount entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		int passwordLength;
		String password, confirmation;
		boolean isMatching;

		passwordLength = request.getModel().getString("password").length();
		errors.state(request, passwordLength >= 5 && passwordLength <= 60, "password", "acme.validation.length", 6, 60);

		password = request.getModel().getString("password");
		confirmation = request.getModel().getString("confirmation");
		isMatching = password.equals(confirmation);
		errors.state(request, isMatching, "confirmation", "anonymous.user-account.error.confirmation-no-match");
	}

	@Override
	public void update(final Request<UserAccount> request, final UserAccount entity) {
		assert request != null;
		assert entity != null;

		this.repository.save(entity);
		for (UserRole role : entity.getRoles()) {
			this.repository.save(role);
		}
	}

	@Override
	public void onSuccess(final Request<UserAccount> request, final Response<UserAccount> response) {
		assert request != null;
		assert response != null;

		if (request.isMethod(HttpMethod.POST)) {
			PrincipalHelper.handleUpdate();
		}
	}

}
