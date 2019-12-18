
package acme.features.authenticated.userthread;

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
public class AuthenticatedUserThreadCreateService implements AbstractCreateService<Authenticated, Userthread> {

	@Autowired
	private AuthenticatedUserThreadRepository repository;


	@Override
	public boolean authorise(final Request<Userthread> request) {
		assert request != null;
		int threadId = request.getModel().getInteger("threadId");
		int id = request.getPrincipal().getActiveRoleId();

		boolean res = this.repository.findOneByThreadIdAndAuthenticatedId(threadId, id).getCreator();

		return res;
	}

	@Override
	public void bind(final Request<Userthread> request, final Userthread entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

	}

	@Override
	public void unbind(final Request<Userthread> request, final Userthread entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

	}

	@Override
	public Userthread instantiate(final Request<Userthread> request) {
		assert request != null;

		Userthread result;
		int authenticatedId;
		int threadId;

		authenticatedId = request.getModel().getInteger("idAuthenticated");
		threadId = request.getModel().getInteger("threadId");

		Authenticated authenticated = this.repository.findOneAuthenticatedById(authenticatedId);
		Messagethread thread = this.repository.findOneById(threadId);

		result = new Userthread();
		result.setAuthenticated(authenticated);
		result.setThread(thread);
		result.setCreator(false);

		return result;
	}

	@Override
	public void validate(final Request<Userthread> request, final Userthread entity, final Errors errors) {
		assert request != null;

	}

	@Override
	public void create(final Request<Userthread> request, final Userthread entity) {
		assert request != null;

		this.repository.save(entity);
	}
}
