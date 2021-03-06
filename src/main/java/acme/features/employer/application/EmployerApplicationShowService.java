
package acme.features.employer.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.applications.Application;
import acme.entities.applications.ApplicationStatus;
import acme.entities.roles.Employer;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractShowService;

@Service
public class EmployerApplicationShowService implements AbstractShowService<Employer, Application> {

	//Internal State

	@Autowired
	private EmployerApplicationRepository repository;


	//AbstractListService<Employer, Duty> interface

	@Override
	public boolean authorise(final Request<Application> request) {
		assert request != null;

		//Para que otro employer no pueda aceptar o rechazar el apply
		Application apply = this.repository.findOneApplicationById(request.getModel().getInteger("id"));
		Integer employerId = apply.getJob().getEmployer().getId();

		boolean result = employerId.equals(request.getPrincipal().getActiveRoleId());

		return result;
	}

	@Override
	public void unbind(final Request<Application> request, final Application entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		//Atributo para el requisito para que justifiquemos la aplicación

		if (entity.getStatus().equals(ApplicationStatus.PENDING)) {
			model.setAttribute("isPending", true);
		}

		model.setAttribute("worker", entity.getWorker().getIdentity().getFullName());
		model.setAttribute("status", entity.getStatus().toString());
		model.setAttribute("job", entity.getJob().getReference());

		request.unbind(entity, model, "referenceNumber", "creationMoment", "statement", "skills", "qualifications", "justification");
	}

	@Override
	public Application findOne(final Request<Application> request) {
		assert request != null;

		Application result;
		int id;

		id = request.getModel().getInteger("id");
		result = this.repository.findOneApplicationById(id);

		return result;
	}

}
