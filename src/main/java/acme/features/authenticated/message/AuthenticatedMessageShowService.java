
package acme.features.authenticated.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.message.Message;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Authenticated;
import acme.framework.services.AbstractShowService;

@Service
public class AuthenticatedMessageShowService implements AbstractShowService<Authenticated, Message> {

	@Autowired
	private AuthenticatedMessageRepository repository;


	@Override
	public boolean authorise(final Request<Message> request) {
		assert request != null;

		Integer threadId = this.repository.findThreadIdByMessageId(request.getModel().getInteger("id"));
		int authenticatedId = request.getPrincipal().getActiveRoleId();
		Integer count = this.repository.countUserThreadByAuIdAndThreadId(authenticatedId, threadId);

		return count == 1;
	}

	@Override
	public void unbind(final Request<Message> request, final Message entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		model.setAttribute("authorName", entity.getAuthor().getUserAccount().getUsername());
		request.unbind(entity, model, "moment", "title", "tags", "body");

	}

	@Override
	public Message findOne(final Request<Message> request) {
		assert request != null;

		Message result;
		int id;

		id = request.getModel().getInteger("id");
		result = this.repository.findOneById(id);

		return result;
	}
}
