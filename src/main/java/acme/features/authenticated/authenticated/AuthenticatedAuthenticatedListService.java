
package acme.features.authenticated.authenticated;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.messagethread.Userthread;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Authenticated;
import acme.framework.services.AbstractListService;

@Service
public class AuthenticatedAuthenticatedListService implements AbstractListService<Authenticated, Authenticated> {

	@Autowired
	private AuthenticatedAuthenticatedRepository repository;


	@Override
	public boolean authorise(final Request<Authenticated> request) {
		assert request != null;

		int threadId = request.getModel().getInteger("threadId");
		int meId = request.getPrincipal().getActiveRoleId();
		Userthread userThread = this.repository.findOneByThreadIdAndAuthenticatedId(threadId, meId);
		Boolean res = userThread.getCreator();

		return res;
	}

	@Override
	public void unbind(final Request<Authenticated> request, final Authenticated entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "userAccount.username");

	}

	@Override
	public Collection<Authenticated> findMany(final Request<Authenticated> request) {
		assert request != null;

		Collection<Authenticated> result;
		int threadId = request.getModel().getInteger("threadId");
		result = this.repository.findUserThreadNotInThread(threadId);

		return result;

	}
}
