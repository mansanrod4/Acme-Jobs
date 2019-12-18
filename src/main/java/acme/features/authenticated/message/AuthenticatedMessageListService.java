
package acme.features.authenticated.message;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.message.Message;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Authenticated;
import acme.framework.services.AbstractListService;

@Service
public class AuthenticatedMessageListService implements AbstractListService<Authenticated, Message> {

	@Autowired
	AuthenticatedMessageRepository repository;


	@Override
	public boolean authorise(final Request<Message> request) {
		assert request != null;

		int idThread = request.getModel().getInteger("id");
		int authenticatedId = request.getPrincipal().getActiveRoleId();

		Integer count = this.repository.countUserThreadByAuIdAndThreadId(authenticatedId, idThread);

		return count == 1;

	}

	@Override
	public void unbind(final Request<Message> request, final Message entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		model.setAttribute("authorName", entity.getAuthor().getUserAccount().getUsername());
		request.unbind(entity, model, "title", "moment", "tags", "body");

	}

	@Override
	public Collection<Message> findMany(final Request<Message> request) {
		assert request != null;

		Collection<Message> result;

		int id;

		id = request.getModel().getInteger("id");

		result = this.repository.findManyByMessagethread(id);

		return result;
	}

}
