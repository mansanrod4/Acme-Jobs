
package acme.features.worker.applications;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.applications.Application;
import acme.entities.applications.ApplicationStatus;
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
		boolean res = false;

		Integer jobId = request.getModel().getInteger("job_id");
		Job job = this.repository.findJobById(jobId);

		if (job.isFinalMode()) {
			res = true;
		}

		return res;
	}

	@Override
	public void bind(final Request<Application> request, final Application entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		Date creationMoment;
		creationMoment = new Date(System.currentTimeMillis() - 1);
		entity.setCreationMoment(creationMoment);
		entity.setUpdateMoment(creationMoment);

		request.bind(entity, errors);
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
		} else {
			request.transfer(model, "jobTitle", "jobReference");
		}
		request.unbind(entity, model, "referenceNumber", "status", "statement", "skills", "qualifications");

	}

	@Override
	public Application instantiate(final Request<Application> request) {
		Application result;
		Worker worker;
		Principal principal;
		result = new Application();

		principal = request.getPrincipal();
		worker = this.repository.findWorkerById(principal.getActiveRoleId());

		Integer jobId = request.getModel().getInteger("job_id");
		Job job = this.repository.findJobById(jobId);

		result.setWorker(worker);
		result.setJob(job);

		result.setStatus(ApplicationStatus.PENDING);

		return result;
	}

	@Override
	public void validate(final Request<Application> request, final Application entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		//		boolean isSpam;
		//
		//		// SPAM FILTER
		//		Double threshold = this.repository.findSysconfig().getThreshold();
		//		String spamWords = this.repository.findSysconfig().getSpamwords();
		//
		//		//Spam - statement
		//		if (!errors.hasErrors("statement")) {
		//			isSpam = SpamFilter.spamFilter(request.getModel().getString("statement"), spamWords, threshold);
		//			errors.state(request, !isSpam, "statement", "employer.duty.error.isSpam");
		//		}
		//
		//		//Spam - skills
		//		if (!errors.hasErrors("skills")) {
		//			isSpam = SpamFilter.spamFilter(request.getModel().getString("skills"), spamWords, threshold);
		//			errors.state(request, !isSpam, "skills", "employer.duty.error.isSpam");
		//		}
		//		//Spam - qualifications
		//		if (!errors.hasErrors("qualifications")) {
		//			isSpam = SpamFilter.spamFilter(request.getModel().getString("qualifications"), spamWords, threshold);
		//			errors.state(request, !isSpam, "qualifications", "employer.duty.error.isSpam");
		//		}

	}

	@Override
	public void create(final Request<Application> request, final Application entity) {
		assert request != null;
		assert entity != null;

		this.repository.save(entity);

	}

}
