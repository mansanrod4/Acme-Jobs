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

package acme.features.authenticated.worker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.roles.Worker;
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
public class AuthenticatedWorkerCreateService implements AbstractCreateService<Authenticated, Worker> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuthenticatedWorkerRepository repository;

	// AbstractCreateService<Authenticated, Worker> ---------------------------


	@Override
	public boolean authorise(final Request<Worker> request) {
		assert request != null;

		return true;
	}

	@Override
	public void validate(final Request<Worker> request, final Worker entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
	}

	@Override
	public void bind(final Request<Worker> request, final Worker entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors);
	}

	@Override
	public void unbind(final Request<Worker> request, final Worker entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "qualifications", "skills");
	}

	@Override
	public Worker instantiate(final Request<Worker> request) {
		assert request != null;

		Worker result;
		Principal principal;
		int userAccountId;
		UserAccount userAccount;

		principal = request.getPrincipal();
		userAccountId = principal.getAccountId();
		userAccount = this.repository.findOneUserAccountById(userAccountId);

		result = new Worker();
		result.setUserAccount(userAccount);

		return result;
	}

	@Override
	public void create(final Request<Worker> request, final Worker entity) {
		assert request != null;
		assert entity != null;

		this.repository.save(entity);
	}

	@Override
	public void onSuccess(final Request<Worker> request, final Response<Worker> response) {
		assert request != null;
		assert response != null;

		if (request.isMethod(HttpMethod.POST)) {
			PrincipalHelper.handleUpdate();
		}
	}

}
