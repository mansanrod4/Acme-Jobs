
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
		// Solo puedo listar los usuarios de un hilo si soy el dueño de ese hilo

		int threadId = request.getModel().getInteger("id");
		int meId = request.getPrincipal().getActiveRoleId();
		Userthread Userthread = this.repository.findOneByThreadIdAndAuthenticatedId(threadId, meId);
		Boolean res = Userthread.getCreator();

		return res;

	}

	@Override
	public void unbind(final Request<Userthread> request, final Userthread entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

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
