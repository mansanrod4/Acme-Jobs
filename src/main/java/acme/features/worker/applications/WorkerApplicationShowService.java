
package acme.features.worker.applications;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.applications.Application;
import acme.entities.applications.ApplicationStatus;
import acme.entities.roles.Worker;
import acme.framework.components.HttpMethod;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractShowService;

@Service
public class WorkerApplicationShowService implements AbstractShowService<Worker, Application> {

	//Internal State

	@Autowired
	private WorkerApplicationRepository repository;


	//AbstractListService<Employer, Job> interface

	@Override
	public boolean authorise(final Request<Application> request) {
		assert request != null;
		boolean result;
		int applicationId;
		Application application;

		applicationId = request.getModel().getInteger("id");
		application = this.repository.findOneApplicationById(applicationId);
		Integer workerId = application.getWorker().getId();

		result = workerId.equals(request.getPrincipal().getActiveRoleId());

		return result;
	}

	@Override
	public void unbind(final Request<Application> request, final Application entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		if (!entity.getStatus().equals(ApplicationStatus.PENDING)) {
			model.setAttribute("isAccepterOrRejected", true);
		}

		if (request.isMethod(HttpMethod.GET)) {
			model.setAttribute("jobTitle", entity.getJob().getTitle());
			model.setAttribute("jobReference", entity.getJob().getReference());
			model.setAttribute("jobDeadline", entity.getJob().getDeadLine());
			model.setAttribute("justification", entity.getJustification());
		} else {
			request.transfer(model, "jobTitle", "jobReference", "jobDeadline", "justification");
		}

		request.unbind(entity, model, "referenceNumber", "status", "creationMoment", "statement", "skills", "qualifications");
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
