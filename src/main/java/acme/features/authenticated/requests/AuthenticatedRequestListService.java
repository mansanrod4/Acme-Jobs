
package acme.features.authenticated.requests;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.requests.Request;
import acme.framework.components.Model;
import acme.framework.entities.Authenticated;
import acme.framework.services.AbstractListService;

@Service
public class AuthenticatedRequestListService implements AbstractListService<Authenticated, Request> {

	@Autowired
	AuthenticatedRequestRepository repository;


	@Override
	public boolean authorise(final acme.framework.components.Request<Request> request) {
		assert request != null;

		return true;
	}

	@Override
	public void unbind(final acme.framework.components.Request<Request> request, final Request entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "ticker", "title", "creationMoment", "deadLine");

	}

	@Override
	public Collection<Request> findMany(final acme.framework.components.Request<Request> request) {
		assert request != null;

		Collection<Request> result;

		result = this.repository.findManyAll();

		return result;
	}

	//AbstractListService<Administrator, Request> interface

}
