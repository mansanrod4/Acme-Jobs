
package acme.features.authenticated.messagethread;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.messagethread.Messagethread;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Authenticated;
import acme.framework.services.AbstractShowService;

@Service
public class AuthenticatedThreadShowService implements AbstractShowService<Authenticated, Messagethread> {

	//Internal State

	@Autowired
	private AuthenticatedThreadRepository repository;


	//AbstractListService<Authenticated, Thread> interface

	@Override
	public boolean authorise(final Request<Messagethread> request) {
		assert request != null;

		return true;
	}

	@Override
	public void unbind(final Request<Messagethread> request, final Messagethread entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		String author = this.repository.findAuthorByThreadId(entity.getId()).getUserAccount().getUsername();

		model.setAttribute("authorName", author);

		request.unbind(entity, model, "moment", "title");
	}

	@Override
	public Messagethread findOne(final Request<Messagethread> request) {
		assert request != null;

		Messagethread result;
		int id;

		id = request.getModel().getInteger("id");
		result = this.repository.findOneThreadById(id);

		return result;
	}

}
