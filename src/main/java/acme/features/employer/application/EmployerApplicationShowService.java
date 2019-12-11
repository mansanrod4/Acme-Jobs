
package acme.features.employer.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.applications.Application;
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

		return true;
	}

	@Override
	public void unbind(final Request<Application> request, final Application entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		model.setAttribute("worker", entity.getWorker().getIdentity().getFullName());
		model.setAttribute("status", entity.getStatus().toString());
		model.setAttribute("job", entity.getJob().getTitle());

		request.unbind(entity, model, "referenceNumber", "creationMoment", "statement", "skills", "qualifications");
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
