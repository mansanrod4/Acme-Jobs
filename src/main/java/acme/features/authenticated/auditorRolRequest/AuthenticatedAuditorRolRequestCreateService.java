
package acme.features.authenticated.auditorRolRequest;

/*
 * AuthenticatedConsumerCreateService.java
 *
 * Copyright (c) 2019 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not AuditorRolRequest any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.rolRequests.AuditorRolRequest;
import acme.framework.components.Errors;
import acme.framework.components.HttpMethod;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Authenticated;
import acme.framework.entities.UserAccount;
import acme.framework.services.AbstractCreateService;

@Service
public class AuthenticatedAuditorRolRequestCreateService implements AbstractCreateService<Authenticated, AuditorRolRequest> {

	//Internal state
	@Autowired
	AuthenticatedAuditorRolRequestRepository repository;


	@Override
	public boolean authorise(final Request<AuditorRolRequest> request) {
		assert request != null;
		return true;
	}

	@Override
	public void bind(final Request<AuditorRolRequest> request, final AuditorRolRequest entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors);
	}

	@Override
	public void unbind(final Request<AuditorRolRequest> request, final AuditorRolRequest entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model);

		if (request.isMethod(HttpMethod.GET)) {
			model.setAttribute("accept", "false");
		} else {
			request.transfer(model, "accept");
		}
	}

	@Override
	public AuditorRolRequest instantiate(final Request<AuditorRolRequest> request) {
		AuditorRolRequest result;
		result = new AuditorRolRequest();

		result.setApproved(false);

		UserAccount ua = this.repository.findOneUserAccountById(request.getPrincipal().getAccountId());
		result.setUser(ua);

		return result;
	}

	@Override
	public void validate(final Request<AuditorRolRequest> request, final AuditorRolRequest entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		UserAccount ua = this.repository.findOneUserAccountById(request.getPrincipal().getAccountId());

		assert entity.getUser().getId() == ua.getId();
	}

	@Override
	public void create(final Request<AuditorRolRequest> request, final AuditorRolRequest entity) {
		assert request != null;
		assert entity != null;

		this.repository.save(entity);
	}
}
