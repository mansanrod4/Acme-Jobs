
package acme.features.authenticated.messagethread;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.messagethread.Messagethread;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Authenticated;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractListService;

@Service
public class AuthenticatedThreadListMineService implements AbstractListService<Authenticated, Messagethread> {

	@Autowired
	AuthenticatedThreadRepository repository;


	//AbstractListService<Authenticated, Thread> interface

	@Override
	public boolean authorise(final Request<Messagethread> request) {
		assert request != null;

		return true;
	}

	@Override
	public Collection<Messagethread> findMany(final Request<Messagethread> request) {
		assert request != null;

		Collection<Messagethread> result = null;
		Principal principal;

		principal = request.getPrincipal();
		result = this.repository.findManyByAuthenticatedId(principal.getActiveRoleId());

		return result;
	}

	@Override
	public void unbind(final Request<Messagethread> request, final Messagethread entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		String author = this.repository.findAuthorByThreadId(entity.getId()).getUserAccount().getUsername();

		model.setAttribute("authorName", author);
		request.unbind(entity, model, "title", "moment");
	}

}
