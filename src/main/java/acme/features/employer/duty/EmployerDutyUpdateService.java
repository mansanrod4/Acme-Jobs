
package acme.features.employer.duty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.components.SpamFilter;
import acme.entities.duties.Duty;
import acme.entities.roles.Employer;
import acme.framework.components.Errors;
import acme.framework.components.HttpMethod;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractUpdateService;

@Service
public class EmployerDutyUpdateService implements AbstractUpdateService<Employer, Duty> {

	//Internal state

	@Autowired
	EmployerDutyRepository repository;


	//AbstractUpdateService<Employer, Duty> interface

	@Override
	public boolean authorise(final Request<Duty> request) {
		assert request != null;

		//Para que otro employer no pueda editar el duty
		Duty duty = this.repository.findOneDutyById(request.getModel().getInteger("id"));
		Integer employerId = duty.getJob().getEmployer().getId();

		boolean result = employerId.equals(request.getPrincipal().getActiveRoleId());

		return result;
	}

	@Override
	public void bind(final Request<Duty> request, final Duty entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors);
	}

	@Override
	public void unbind(final Request<Duty> request, final Duty entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

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
	public Duty findOne(final Request<Duty> request) {
		assert request != null;
		Duty result;
		int id;
		id = request.getModel().getInteger("id");
		result = this.repository.findOneDutyById(id);

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
			errors.state(request, !isSpam, "description", "employer.job.error.isSpam");
		}

		//Spam - Title
		if (!errors.hasErrors("title")) {
			isSpam = SpamFilter.spamFilter(request.getModel().getString("title"), spamWords, threshold);
			errors.state(request, !isSpam, "title", "employer.job.error.isSpam");
		}

	}

	@Override
	public void update(final Request<Duty> request, final Duty entity) {
		assert request != null;
		assert entity != null;

		this.repository.save(entity);

	}
}
