
package acme.features.authenticated.userthread;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.messagethread.Userthread;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Authenticated;
import acme.framework.services.AbstractShowService;

@Service
public class AuthenticatedUserThreadShowService implements AbstractShowService<Authenticated, Userthread> {

	@Autowired
	private AuthenticatedUserThreadRepository repository;


	@Override
	public boolean authorise(final Request<Userthread> request) {
		assert request != null;
		// Solo puedes ver un usuario de un hilo si tu eres el creador de dicho hilo
		int UserthreadId = request.getModel().getInteger("id");
		Userthread Userthread = this.repository.findOneUserThreadById(UserthreadId);
		int threadId = Userthread.getThread().getId();
		int meId = request.getPrincipal().getActiveRoleId();
		Userthread Userthread2 = this.repository.findOneByThreadIdAndAuthenticatedId(threadId, meId);
		Boolean res = Userthread2.getCreator();

		return res;
	}

	@Override
	public void unbind(final Request<Userthread> request, final Userthread entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "authenticated.userAccount.username", "creatorThread");
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
}
