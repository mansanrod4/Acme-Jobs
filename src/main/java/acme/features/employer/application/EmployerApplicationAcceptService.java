
package acme.features.employer.application;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.applications.Application;
import acme.entities.applications.ApplicationStatus;
import acme.entities.roles.Employer;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractUpdateService;

@Service
public class EmployerApplicationAcceptService implements AbstractUpdateService<Employer, Application> {

	//Internal state

	@Autowired
	EmployerApplicationRepository repository;


	//AbstractUpdateService<Employer, Application> interface

	@Override
	public boolean authorise(final Request<Application> request) {
		assert request != null;

		//No se puede aceptar o rechazar si ya esta aceptado o rechazado

		boolean isPending = true;

		Integer ApplyId = request.getModel().getInteger("id");
		ApplicationStatus status = this.repository.findOneApplicationById(ApplyId).getStatus();

		if (status.equals(ApplicationStatus.REJECTED) || status.equals(ApplicationStatus.ACCEPTED)) {
			isPending = false;
		}

		return isPending;
	}

	@Override
	public void bind(final Request<Application> request, final Application entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors, "status", "worker", "job");
	}

	@Override
	public void unbind(final Request<Application> request, final Application entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		model.setAttribute("isPending", true);

		request.unbind(entity, model);
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

	@Override
	public void validate(final Request<Application> request, final Application entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
	}

	@Override
	public void update(final Request<Application> request, final Application entity) {
		assert request != null;
		assert entity != null;

		Date moment;
		moment = new Date(System.currentTimeMillis() - 1);

		entity.setStatus(ApplicationStatus.ACCEPTED);
		entity.setLastModification(moment);

		this.repository.save(entity);

	}
}
