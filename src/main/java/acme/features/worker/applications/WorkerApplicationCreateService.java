
package acme.features.worker.applications;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.applications.Application;
import acme.entities.jobs.Job;
import acme.entities.roles.Worker;
import acme.framework.components.Errors;
import acme.framework.components.HttpMethod;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractCreateService;

@Service
public class WorkerApplicationCreateService implements AbstractCreateService<Worker, Application> {

	//Internal state
	@Autowired
	private WorkerApplicationRepository repository;


	@Override
	public boolean authorise(final Request<Application> request) {
		assert request != null;
		return true;
	}

	@Override
	public void bind(final Request<Application> request, final Application entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		Date creationMoment;
		creationMoment = new Date(System.currentTimeMillis() - 1);
		entity.setCreationMoment(creationMoment);

		request.bind(entity, errors, "creationMoment", "job", "worker");
	}

	@Override
	public void unbind(final Request<Application> request, final Application entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		Integer jobId = request.getModel().getInteger("job_id");
		model.setAttribute("job_id", jobId);
		if (request.isMethod(HttpMethod.GET)) {

			model.setAttribute("jobTitle", entity.getJob().getTitle());
			model.setAttribute("jobReference", entity.getJob().getReference());
			model.setAttribute("jobEmployer", entity.getJob().getEmployer().getUserAccount().getUsername());
		} else {
			request.transfer(model, "jobTitle", "jobReference", "jobEmployer");
		}
		request.unbind(entity, model);

	}

	@Override
	public Application instantiate(final Request<Application> request) {
		Application result;
		Worker worker;
		Principal principal;

		principal = request.getPrincipal();

		worker = this.repository.findWorkerById(principal.getActiveRoleId());
		Integer jobId = request.getModel().getInteger("job_id");
		Job job = this.repository.findJobById(jobId);
		result = new Application();

		result.setWorker(worker);
		result.setJob(job);

		return result;
	}

	@Override
	public void validate(final Request<Application> request, final Application entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		//		entity.getJob().getDeadLine();
		//
		//		Integer jobId = entity.getJob().getId();

	}

	@Override
	public void create(final Request<Application> request, final Application entity) {
		// TODO Auto-generated method stub
		Date moment;
		moment = new Date(System.currentTimeMillis() - 1);
		entity.setCreationMoment(moment);

		Principal principal;
		Worker worker;

		principal = request.getPrincipal();
		worker = this.repository.findWorkerById(principal.getActiveRoleId());

		entity.setWorker(worker);
		this.repository.save(entity);

	}

}
