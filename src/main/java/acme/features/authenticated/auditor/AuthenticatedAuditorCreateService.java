
package acme.features.authenticated.auditor;

/*
 * AuthenticatedConsumerCreateService.java
 *
 * Copyright (c) 2019 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not Auditor any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.rolRequests.AuditorRolRequest;
import acme.entities.roles.Auditor;
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
public class AuthenticatedAuditorCreateService implements AbstractCreateService<Authenticated, Auditor> {

	//Internal state

	@Autowired
	AuthenticatedAuditorRepository repository;


	@Override
	public boolean authorise(final Request<Auditor> request) {
		assert request != null;

		return true;
	}

	@Override
	public void bind(final Request<Auditor> request, final Auditor entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors);

	}

	@Override
	public void unbind(final Request<Auditor> request, final Auditor entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		UserAccount ua = this.repository.findOneUserAccountById(request.getPrincipal().getAccountId());
		AuditorRolRequest arr = this.repository.findOneAuditorRolRequestByUserAccountId(ua.getId());

		if (arr == null) {
			model.setAttribute("hasRequest", false);
		} else {
			model.setAttribute("hasRequest", true);
			if (arr.isApproved()) {
				model.setAttribute("isApproved", true);
			} else {
				model.setAttribute("isApproved", false);
			}
		}

		request.unbind(entity, model);

		if (request.isMethod(HttpMethod.GET)) {
			model.setAttribute("accept", "false");
		} else {
			request.transfer(model, "accept");
		}
	}

	@Override
	public Auditor instantiate(final Request<Auditor> request) {
		Auditor result;
		Principal principal;
		int userAccountId;
		UserAccount userAccount;

		principal = request.getPrincipal();
		userAccountId = principal.getAccountId();
		userAccount = this.repository.findOneUserAccountById(userAccountId);

		result = new Auditor();
		result.setUserAccount(userAccount);

		return result;
	}

	@Override
	public void validate(final Request<Auditor> request, final Auditor entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		UserAccount ua = this.repository.findOneUserAccountById(request.getPrincipal().getAccountId());

		assert entity.getUserAccount().getId() == ua.getId();
	}

	@Override
	public void create(final Request<Auditor> request, final Auditor entity) {
		assert request != null;
		assert entity != null;

		this.repository.save(entity);
	}

	@Override
	public void onSuccess(final Request<Auditor> request, final Response<Auditor> response) {
		assert request != null;
		assert response != null;

		if (request.isMethod(HttpMethod.POST)) {
			PrincipalHelper.handleUpdate();
		}
	}
}
