
package acme.features.administrator.auditorRolRequest;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.rolRequests.AuditorRolRequest;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Administrator;
import acme.framework.services.AbstractListService;

@Service
public class AdministratorAuditorRolRequestListService implements AbstractListService<Administrator, AuditorRolRequest> {

	@Autowired
	public AdministratorAuditorRolRequestRepository repository;


	//AbstractListService<Administrator, Announcement> interface

	@Override
	public boolean authorise(final Request<AuditorRolRequest> request) {
		assert request != null;

		return true;
	}

	@Override
	public Collection<AuditorRolRequest> findMany(final Request<AuditorRolRequest> request) {
		assert request != null;

		Collection<AuditorRolRequest> result;

		result = this.repository.findAllAuditorRolRequest();

		return result;
	}

	@Override
	public void unbind(final Request<AuditorRolRequest> request, final AuditorRolRequest entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "getUserName()", "approved");
	}

}
