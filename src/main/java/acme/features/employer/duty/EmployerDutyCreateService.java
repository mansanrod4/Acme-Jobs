
package acme.features.employer.duty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.components.SpamFilter;
import acme.entities.duties.Duty;
import acme.entities.jobs.Job;
import acme.entities.roles.Employer;
import acme.framework.components.Errors;
import acme.framework.components.HttpMethod;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractCreateService;

@Service
public class EmployerDutyCreateService implements AbstractCreateService<Employer, Duty> {

	//Internal state

	@Autowired
	EmployerDutyRepository repository;


	@Override
	public boolean authorise(final Request<Duty> request) {
		assert request != null;

		//Para que otro employer no pueda crear el duty a un job que no es suyo
		Job duty = this.repository.findJobById(request.getModel().getInteger("job_id"));
		Integer employerId = duty.getEmployer().getId();

		boolean result = employerId.equals(request.getPrincipal().getActiveRoleId());

		return result;
	}

	@Override
	public void bind(final Request<Duty> request, final Duty entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors, "job");

	}

	@Override
	public void unbind(final Request<Duty> request, final Duty entity, final Model model) {
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

		request.unbind(entity, model, "title", "percentageTimeWeek", "description");
	}

	@Override
	public Duty instantiate(final Request<Duty> request) {
		Duty result;
		result = new Duty();

		Integer jobId = request.getModel().getInteger("job_id");
		Job job = this.repository.findJobById(jobId);
		result.setJob(job);

		return result;
	}

	@Override
	public void validate(final Request<Duty> request, final Duty entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		boolean isSpam;

		// SPAM FILTER
		Double threshold = this.repository.findSysconfig().getThreshold();
		String spamWords = this.repository.findSysconfig().getSpamwords();

		//Spam - Description
		if (!errors.hasErrors("description")) {
			isSpam = SpamFilter.spamFilter(request.getModel().getString("description"), spamWords, threshold);
			errors.state(request, !isSpam, "description", "employer.duty.error.isSpam");
		}

		//Spam - Title
		if (!errors.hasErrors("title")) {
			isSpam = SpamFilter.spamFilter(request.getModel().getString("title"), spamWords, threshold);
			errors.state(request, !isSpam, "title", "employer.duty.error.isSpam");
		}

	}

	@Override
	public void create(final Request<Duty> request, final Duty entity) {
		assert request != null;
		assert entity != null;

		this.repository.save(entity);

	}
}
