
package acme.features.administrator.investor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.investor.Investor;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Administrator;
import acme.framework.services.AbstractDeleteService;

@Service
public class AdministratorInvestorDeleteService implements AbstractDeleteService<Administrator, Investor> {

	//Internal state

	@Autowired
	AdministratorInvestorRepository repository;


	//AbstractUpdateService<Administrator, Investor> interface

	@Override
	public boolean authorise(final Request<Investor> request) {
		assert request != null;
		return true;
	}

	@Override
	public void bind(final Request<Investor> request, final Investor entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors);

	}

	@Override
	public void unbind(final Request<Investor> request, final Investor entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "name", "sector", "investingStatement", "star");
	}

	@Override
	public Investor findOne(final Request<Investor> request) {
		assert request != null;
		Investor result;
		int id;
		id = request.getModel().getInteger("id");
		result = this.repository.findOneInvestorById(id);

		return result;
	}

	@Override
	public void validate(final Request<Investor> request, final Investor entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
	}

	@Override
	public void delete(final Request<Investor> request, final Investor entity) {
		assert request != null;
		assert entity != null;

		this.repository.delete(entity);

	}
}
