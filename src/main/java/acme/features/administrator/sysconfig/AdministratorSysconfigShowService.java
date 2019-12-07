
package acme.features.administrator.sysconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.sysconfig.Sysconfig;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Administrator;
import acme.framework.services.AbstractShowService;

@Service
public class AdministratorSysconfigShowService implements AbstractShowService<Administrator, Sysconfig> {

	//Internal State

	@Autowired
	private AdministratorSysconfigRepository repository;


	//AbstractListService<Administrator, Announcement> interface

	@Override
	public boolean authorise(final Request<Sysconfig> request) {
		assert request != null;

		return true;
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
	public void unbind(final Request<Sysconfig> request, final Sysconfig entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "spamwords", "threshold");
	}

}
