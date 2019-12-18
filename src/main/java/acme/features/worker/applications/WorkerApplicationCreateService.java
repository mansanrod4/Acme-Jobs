
package acme.features.worker.applications;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.components.SpamFilter;
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

		boolean isActive = false;

		//EL TRABAJO
		Integer jobId = request.getModel().getInteger("job_id");
		Job job = this.repository.findJobById(jobId);

		//LA FECHA ACTUAL
		Date actualMoment;
		actualMoment = new Date(System.currentTimeMillis() - 1);

		//Solo se puede crear si está publicado y el deadline activo
		if (job.isFinalMode() && job.getDeadLine().after(actualMoment)) {
			isActive = true;
		}

		return isActive;
	}

	@Override
	public void bind(final Request<Application> request, final Application entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		Date creationMoment;
		creationMoment = new Date(System.currentTimeMillis() - 1);
		entity.setCreationMoment(creationMoment);
		entity.setLastModification(creationMoment);

		request.bind(entity, errors, "creationMoment", "lastModification", "status");
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
			model.setAttribute("jobDeadline", entity.getJob().getDeadLine());
		} else {
			request.transfer(model, "jobTitle", "jobReference", "jobDeadline");
		}
		request.unbind(entity, model, "referenceNumber", "status", "statement", "skills", "qualifications");

	}

	@Override
	public Application instantiate(final Request<Application> request) {
		Application result = new Application();
		Principal principal = request.getPrincipal();
		Worker worker = this.repository.findWorkerById(principal.getActiveRoleId());

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

		boolean isSpam, isDuplicated, hasPendingApply = false;

		//Validaciones

		//Referencia única
		isDuplicated = this.repository.findOneApplicationByTicker(entity.getReferenceNumber()) != null;
		errors.state(request, !isDuplicated, "referenceNumber", "worker.application.error.duplicated");

		//No puedes aplicar a un job si ya tienes una aplicacion aceptada en ese job
		Worker worker = this.repository.findWorkerById(request.getPrincipal().getActiveRoleId());
		Integer jobId = request.getModel().getInteger("job_id");

		for (Application a : this.repository.findManyByWorkerId(worker.getId())) {
			Integer aJobId = a.getJob().getId();
			if (aJobId.equals(jobId) && a.getStatus().equals(ApplicationStatus.ACCEPTED)) {
				hasPendingApply = true;
			}
		}
		errors.state(request, !hasPendingApply, "jobDeadline", "worker.application.error.alreadyApplied");

		// SPAM FILTER
		Double threshold = this.repository.findSysconfig().getThreshold();
		String spamWords = this.repository.findSysconfig().getSpamwords();

		//Spam - statement
		if (!errors.hasErrors("statement")) {
			isSpam = SpamFilter.spamFilter(request.getModel().getString("statement"), spamWords, threshold);
			errors.state(request, !isSpam, "statement", "worker.application.error.isSpam");
		}

		//Spam - skills
		if (!errors.hasErrors("skills")) {
			isSpam = SpamFilter.spamFilter(request.getModel().getString("skills"), spamWords, threshold);
			errors.state(request, !isSpam, "skills", "worker.application.error.isSpam");
		}

		//Spam - qualifications
		if (!errors.hasErrors("qualifications")) {
			isSpam = SpamFilter.spamFilter(request.getModel().getString("qualifications"), spamWords, threshold);
			errors.state(request, !isSpam, "qualifications", "worker.application.error.isSpam");
		}

	}

	@Override
	public void create(final Request<Application> request, final Application entity) {
		assert request != null;
		assert entity != null;

		//Si ya tienes una aplicacion PENDING en este job, borra la que tenias y añade la nueva
		Worker worker = this.repository.findWorkerById(request.getPrincipal().getActiveRoleId());
		Integer jobId = request.getModel().getInteger("job_id");

		for (Application a : this.repository.findManyByWorkerId(worker.getId())) {
			Integer aJobId = a.getJob().getId();
			if (aJobId.equals(jobId) && a.getStatus().equals(ApplicationStatus.PENDING)) {
				this.repository.delete(a);
			}
		}

		this.repository.save(entity);

	}

}
