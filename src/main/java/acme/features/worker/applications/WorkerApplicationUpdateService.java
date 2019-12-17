
package acme.features.worker.applications;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.applications.Application;
import acme.entities.applications.ApplicationStatus;
import acme.entities.roles.Worker;
import acme.framework.components.Errors;
import acme.framework.components.HttpMethod;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractUpdateService;

@Service
public class WorkerApplicationUpdateService implements AbstractUpdateService<Worker, Application> {

	//Internal state
	@Autowired
	private WorkerApplicationRepository repository;


	@Override
	public boolean authorise(final Request<Application> request) {
		assert request != null;
		boolean result;

		Application application = this.repository.findOneApplicationById(request.getModel().getInteger("id"));
		Integer workerId = application.getWorker().getId();

		result = workerId.equals(request.getPrincipal().getActiveRoleId()) && !this.repository.findOneApplicationById(request.getModel().getInteger("id")).getStatus().equals(ApplicationStatus.ACCEPTED);

		return result;
	}

	@Override
	public void bind(final Request<Application> request, final Application entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors);
	}

	@Override
	public void unbind(final Request<Application> request, final Application entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		if (request.isMethod(HttpMethod.GET)) {
			model.setAttribute("jobTitle", entity.getJob().getTitle());
			model.setAttribute("jobReference", entity.getJob().getReference());
		} else {
			request.transfer(model, "jobTitle", "jobReference");
		}

		request.unbind(entity, model, "referenceNumber", "status", "statement", "skills", "qualifications");

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
	public void update(final Request<Application> request, final Application entity) {
		assert request != null;
		assert entity != null;

		Date updateMoment;
		updateMoment = new Date(System.currentTimeMillis() - 1);
		entity.setUpdateMoment(updateMoment);

		this.repository.save(entity);

	}

}
