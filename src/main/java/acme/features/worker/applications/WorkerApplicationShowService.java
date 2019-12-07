
package acme.features.worker.applications;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.applications.Application;
import acme.entities.roles.Worker;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Principal;
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
		Worker worker;
		Principal principal;

		applicationId = request.getModel().getInteger("id");
		application = this.repository.findOneApplicationById(applicationId);
		worker = application.getWorker();
		principal = request.getPrincipal();
		result = worker.getUserAccount().getId() == principal.getAccountId();

		//		application.getStatus() || !application.isStatus() &&

		return result;
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
		//		request.unbind(entity, model, "statement", "skills", "qualifications");
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
