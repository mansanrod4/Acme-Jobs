
package acme.features.authenticated.messagethread;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.messagethread.Messagethread;
import acme.entities.messagethread.Userthread;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Authenticated;
import acme.framework.services.AbstractCreateService;

@Service
public class AuthenticatedThreadCreateService implements AbstractCreateService<Authenticated, Messagethread> {

	//Internal state

	@Autowired
	AuthenticatedThreadRepository repository;


	@Override
	public boolean authorise(final Request<Messagethread> request) {
		assert request != null;
		return true;
	}

	@Override
	public void bind(final Request<Messagethread> request, final Messagethread entity, final Errors errors) {

		assert request != null;
		assert entity != null;
		assert errors != null;

		Date moment;
		moment = new Date(System.currentTimeMillis() - 1);
		entity.setMoment(moment);

		request.bind(entity, errors, "moment");
	}

	@Override
	public void unbind(final Request<Messagethread> request, final Messagethread entity, final Model model) {

		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "title");

	}

	@Override
	public Messagethread instantiate(final Request<Messagethread> request) {
		Messagethread thread;

		thread = new Messagethread();
		return thread;
	}

	@Override
	public void validate(final Request<Messagethread> request, final Messagethread entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

	}

	@Override
	public void create(final Request<Messagethread> request, final Messagethread entity) {
		Date moment;
		moment = new Date(System.currentTimeMillis() - 1);
		entity.setMoment(moment);

		Userthread userThread = new Userthread();
		Authenticated au = this.repository.findAuthenticatedById(request.getPrincipal().getActiveRoleId());
		userThread.setAuthenticated(au);
		userThread.setThread(entity);
		userThread.setCreator(true);

		this.repository.save(entity);
		this.repository.save(userThread);

	}

}
