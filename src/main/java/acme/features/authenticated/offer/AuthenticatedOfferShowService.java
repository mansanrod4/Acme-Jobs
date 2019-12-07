
package acme.features.authenticated.offer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.offers.Offer;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Authenticated;
import acme.framework.services.AbstractShowService;

@Service
public class AuthenticatedOfferShowService implements AbstractShowService<Authenticated, Offer> {

	//Internal State

	@Autowired
	private AuthenticatedOfferRepository repository;


	//AbstractListService<Administrator, Offer> interface

	@Override
	public boolean authorise(final Request<Offer> request) {
		assert request != null;

		return true;
	}

	@Override
	public Offer findOne(final Request<Offer> request) {
		assert request != null;

		Offer result;
		int id;

		id = request.getModel().getInteger("id");
		result = this.repository.findOneById(id);

		return result;
	}

	@Override
	public void unbind(final Request<Offer> request, final Offer entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "title", "moment", "deadLine", "text", "money", "ticker");
	}

}
