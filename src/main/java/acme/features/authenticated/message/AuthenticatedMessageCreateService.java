
package acme.features.authenticated.message;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.components.SpamFilter;
import acme.entities.message.Message;
import acme.entities.messagethread.Messagethread;
import acme.entities.sysconfig.Sysconfig;
import acme.framework.components.Errors;
import acme.framework.components.HttpMethod;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Authenticated;
import acme.framework.services.AbstractCreateService;

@Service
public class AuthenticatedMessageCreateService implements AbstractCreateService<Authenticated, Message> {

	@Autowired
	AuthenticatedMessageRepository repository;


	@Override
	public boolean authorise(final Request<Message> request) {
		assert request != null;

		int idThread = request.getModel().getInteger("threadId");
		int authenticatedId = request.getPrincipal().getActiveRoleId();

		Integer count = this.repository.countUserThreadByAuIdAndThreadId(authenticatedId, idThread);

		return count == 1;

	}

	@Override
	public void bind(final Request<Message> request, final Message entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		Date moment;
		Authenticated au = new Authenticated();
		au = this.repository.findAuthenticatedById(request.getPrincipal().getActiveRoleId());
		moment = new Date(System.currentTimeMillis() - 1);

		entity.setMoment(moment);
		entity.setAuthor(au);

		request.bind(entity, errors, "moment", "author");

	}

	@Override
	public void unbind(final Request<Message> request, final Message entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		if (request.isMethod(HttpMethod.GET)) {
			model.setAttribute("confirm", "false");
		} else {
			request.transfer(model, "confirm");
		}

		request.unbind(entity, model, "title", "tags", "body");

	}

	@Override
	public Message instantiate(final Request<Message> request) {
		Message result;
		result = new Message();

		return result;

	}

	@Override
	public void validate(final Request<Message> request, final Message entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		Sysconfig sys = this.repository.findspam();

		Boolean isConfirmed = request.getModel().getBoolean("confirm");
		Boolean bodyspam = SpamFilter.spamFilter(entity.getBody(), sys.getSpamwords(), sys.getThreshold());
		Boolean tagspam = SpamFilter.spamFilter(entity.getTags(), sys.getSpamwords(), sys.getThreshold());
		Boolean titlespam = SpamFilter.spamFilter(entity.getTitle(), sys.getSpamwords(), sys.getThreshold());

		errors.state(request, isConfirmed, "confirm", "authenticated.message.create.error.must-confirm");
		errors.state(request, !titlespam, "title", "authenticated.message.create.error.titlespam");
		errors.state(request, !bodyspam, "body", "authenticated.message.create.error.bodyspam");
		errors.state(request, !tagspam, "tags", "authenticated.message.create.error.tagspam");

	}

	@Override
	public void create(final Request<Message> request, final Message entity) {
		assert request != null;
		assert entity != null;
		Messagethread thread;
		Date moment;
		Authenticated au = new Authenticated();

		au = this.repository.findAuthenticatedById(request.getPrincipal().getActiveRoleId());
		moment = new Date(System.currentTimeMillis() - 1);

		entity.setMoment(moment);
		entity.setAuthor(au);

		this.repository.save(entity);

		int threadId = request.getModel().getInteger("threadId");
		Collection<Message> messages = this.repository.findManyByMessagethread(threadId);
		messages.add(entity);

		thread = this.repository.findThreadById(threadId);
		thread.setMessages(messages);

		this.repository.save(thread);

	}

}
