
package acme.features.administrator.sysconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.sysconfig.Sysconfig;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Administrator;
import acme.framework.services.AbstractUpdateService;

@Service
public class AdministratorSysconfigUpdateService implements AbstractUpdateService<Administrator, Sysconfig> {

	//Internal state

	@Autowired
	AdministratorSysconfigRepository repository;


	//AbstractUpdateService<Administrator, Sysconfig> interface

	@Override
	public boolean authorise(final Request<Sysconfig> request) {
		assert request != null;

		return true;
	}

	@Override
	public void bind(final Request<Sysconfig> request, final Sysconfig entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors);
	}

	@Override
	public void unbind(final Request<Sysconfig> request, final Sysconfig entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "spamwords", "threshold");
	}

	@Override
	public Sysconfig findOne(final Request<Sysconfig> request) {
		assert request != null;

		Sysconfig result;
		int id;

		id = request.getModel().getInteger("id");
		result = this.repository.findOneById(id);

		return result;
	}

	@Override
	public void validate(final Request<Sysconfig> request, final Sysconfig entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
	}

	@Override
	public void update(final Request<Sysconfig> request, final Sysconfig entity) {
		assert request != null;
		assert entity != null;

		this.repository.save(entity);
	}

}
