
package acme.features.administrator.sysconfig;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.sysconfig.Sysconfig;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Administrator;
import acme.framework.services.AbstractListService;

@Service
public class AdministratorSysconfigListService implements AbstractListService<Administrator, Sysconfig> {

	@Autowired
	AdministratorSysconfigRepository repository;


	//AbstractListService<Administrator, Announcement> interface

	@Override
	public boolean authorise(final Request<Sysconfig> request) {
		assert request != null;

		return true;
	}

	@Override
	public Collection<Sysconfig> findMany(final Request<Sysconfig> request) {
		assert request != null;

		Collection<Sysconfig> result;

		result = this.repository.findManyAll();

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
