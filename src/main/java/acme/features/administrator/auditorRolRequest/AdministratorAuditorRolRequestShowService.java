
package acme.features.administrator.auditorRolRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.rolRequests.AuditorRolRequest;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Administrator;
import acme.framework.services.AbstractShowService;

@Service
public class AdministratorAuditorRolRequestShowService implements AbstractShowService<Administrator, AuditorRolRequest> {

	//Internal State

	@Autowired
	private AdministratorAuditorRolRequestRepository repository;


	//AbstractListService<Employer, Duty> interface

	@Override
	public boolean authorise(final Request<AuditorRolRequest> request) {
		assert request != null;

		return true;
	}

	@Override
	public void unbind(final Request<AuditorRolRequest> request, final AuditorRolRequest entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		//Atributo para el requisito para que justifiquemos la aplicación

		model.setAttribute("user", entity.getUser().getIdentity().getFullName());

		request.unbind(entity, model);
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

}
