
package acme.features.authenticated.userthread;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.messagethread.Userthread;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Authenticated;
import acme.framework.services.AbstractDeleteService;

@Service
public class AuthenticatedUserThreadDeleteService implements AbstractDeleteService<Authenticated, Userthread> {

	@Autowired
	private AuthenticatedUserThreadRepository repository;


	@Override
	public boolean authorise(final Request<Userthread> request) {
		assert request != null;

		int UserthreadId = request.getModel().getInteger("id");
		int threadId = this.repository.findOneUserThreadById(UserthreadId).getThread().getId();
		int id = request.getPrincipal().getActiveRoleId();

		boolean res = this.repository.findOneByThreadIdAndAuthenticatedId(threadId, id).getCreator();
		return res;
	}

	@Override
	public void bind(final Request<Userthread> request, final Userthread entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors);
	}

	@Override
	public void unbind(final Request<Userthread> request, final Userthread entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "authenticated.userAccount.username");
	}

	@Override
	public Userthread findOne(final Request<Userthread> request) {
		assert request != null;

		Userthread result;
		int id;

		id = request.getModel().getInteger("id");
		result = this.repository.findOneUserThreadById(id);

		return result;
	}

	@Override
	public void validate(final Request<Userthread> request, final Userthread entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		int threadId = request.getModel().getInteger("id");
		Userthread Userthread = this.repository.findOneUserThreadById(threadId);
		int authenticatedId = Userthread.getAuthenticated().getId();
		int id = request.getPrincipal().getActiveRoleId();
		boolean sameUser = id == authenticatedId;
		errors.state(request, !sameUser, "authenticated.userAccount.username", "authenticated.userthread.delete.creator");

	}

	@Override
	public void delete(final Request<Userthread> request, final Userthread entity) {
		assert request != null;
		assert entity != null;

		this.repository.delete(entity);
	}

}
