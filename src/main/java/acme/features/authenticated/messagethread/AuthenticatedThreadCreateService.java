
package acme.features.authenticated.messagethread;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.components.SpamFilter;
import acme.entities.messagethread.Messagethread;
import acme.entities.messagethread.Userthread;
import acme.entities.sysconfig.Sysconfig;
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

		Date moment;
		moment = new Date(System.currentTimeMillis() - 1);
		thread.setMoment(moment);

		return thread;
	}

	@Override
	public void validate(final Request<Messagethread> request, final Messagethread entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		Sysconfig sys = this.repository.findspam();

		Boolean titlespam = SpamFilter.spamFilter(entity.getTitle(), sys.getSpamwords(), sys.getThreshold());

		errors.state(request, !titlespam, "title", "authenticated.messagethread.create.error.titlespam");

	}

	@Override
	public void create(final Request<Messagethread> request, final Messagethread entity) {

		Userthread userThread = new Userthread();
		Authenticated au = this.repository.findAuthenticatedById(request.getPrincipal().getActiveRoleId());
		userThread.setAuthenticated(au);
		userThread.setThread(entity);
		userThread.setCreator(true);

		this.repository.save(entity);
		this.repository.save(userThread);

	}

}
