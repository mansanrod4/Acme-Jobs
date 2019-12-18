
package acme.features.worker.jobs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.applications.Application;
import acme.entities.applications.ApplicationStatus;
import acme.entities.jobs.Job;
import acme.entities.roles.Worker;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractShowService;

@Service
public class WorkerJobShowService implements AbstractShowService<Worker, Job> {

	//Internal State

	@Autowired
	private WorkerJobRepository repository;


	//AbstractListService<Worker, Job> interface

	@Override
	public boolean authorise(final Request<Job> request) {
		assert request != null;

		return true;
	}

	@Override
	public void unbind(final Request<Job> request, final Job entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		//Si el worker no tiene apply acepted en este job
		Worker worker = this.repository.findWorkerById(request.getPrincipal().getActiveRoleId());
		Integer jobId = request.getModel().getInteger("id");

		for (Application a : this.repository.findManyByWorkerId(worker.getId())) {
			Integer aJobId = a.getJob().getId();
			if (aJobId.equals(jobId) && a.getStatus().equals(ApplicationStatus.ACCEPTED)) {
				model.setAttribute("noAppliesAcceptedYet", true);
			}
		}

		request.unbind(entity, model, "reference", "title", "deadLine");
		request.unbind(entity, model, "salary", "moreInfo", "description", "finalMode");
	}

	@Override
	public Job findOne(final Request<Job> request) {
		assert request != null;

		Job result;
		int id;

		id = request.getModel().getInteger("id");
		result = this.repository.findOneJobById(id);

		return result;
	}

}
