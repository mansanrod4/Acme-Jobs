
package acme.features.administrator.auditorRolRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.rolRequests.AuditorRolRequest;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Administrator;
import acme.framework.services.AbstractUpdateService;

@Service
public class AdministratorAuditorRolRequestAcceptService implements AbstractUpdateService<Administrator, AuditorRolRequest> {

	//Internal State

	@Autowired
	private AdministratorAuditorRolRequestRepository repository;


	//AbstractListService<Administrator, AuditorRolRequest> interface

	@Override
	public boolean authorise(final Request<AuditorRolRequest> request) {
		assert request != null;

		AuditorRolRequest req;
		int id;
		id = request.getModel().getInteger("id");
		req = this.repository.findOneAuditorRolRequestById(id);

		if (req.isApproved()) {
			return false;
		}

		return true;
	}

	@Override
	public AuditorRolRequest findOne(final Request<AuditorRolRequest> request) {
		assert request != null;

		AuditorRolRequest result;
		int id;

		id = request.getModel().getInteger("id");
		result = this.repository.findOneAuditorRolRequestById(id);

		return result;
	}

	@Override
	public void unbind(final Request<AuditorRolRequest> request, final AuditorRolRequest entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		model.setAttribute("isApproved", false);
		request.unbind(entity, model);
	}

	@Override
	public void bind(final Request<AuditorRolRequest> request, final AuditorRolRequest entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors, "user");
	}

	@Override
	public void validate(final Request<AuditorRolRequest> request, final AuditorRolRequest entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
	}

	@Override
	public void update(final Request<AuditorRolRequest> request, final AuditorRolRequest entity) {
		assert request != null;
		assert entity != null;

		entity.setApproved(true);

		this.repository.save(entity);
	}

}
