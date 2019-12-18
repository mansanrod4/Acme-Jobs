
package acme.features.authenticated.userthread;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.messagethread.Userthread;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Authenticated;
import acme.framework.services.AbstractListService;

@Service
public class AuthenticatedUserThreadListService implements AbstractListService<Authenticated, Userthread> {

	@Autowired
	private AuthenticatedUserThreadRepository repository;


	@Override
	public boolean authorise(final Request<Userthread> request) {
		assert request != null;

		int idThread = request.getModel().getInteger("id");
		int authenticatedId = request.getPrincipal().getActiveRoleId();

		Integer count = this.repository.countUserThreadByAuIdAndThreadId(authenticatedId, idThread);

		return count == 1;

	}

	@Override
	public void unbind(final Request<Userthread> request, final Userthread entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		int threadId = request.getModel().getInteger("id");
		int id = request.getPrincipal().getActiveRoleId();
		boolean res = this.repository.findOneByThreadIdAndAuthenticatedId(threadId, id).getCreator() == true;

		model.setAttribute("creator", res);

		request.unbind(entity, model, "authenticated.userAccount.username");

	}

	@Override
	public Collection<Userthread> findMany(final Request<Userthread> request) {
		assert request != null;

		Collection<Userthread> result;
		int threadId = request.getModel().getInteger("id");

		result = this.repository.findUserThreadsInThread(threadId);

		return result;

	}

}
