
package acme.features.employer.duty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.duties.Duty;
import acme.entities.jobs.Job;
import acme.entities.roles.Employer;
import acme.entities.sysconfig.Sysconfig;
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
		return true;
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

		boolean over100, isSpam = false;

		//No se puede crear una tarea si su timeWeekPercentage supera el 100 si se suma con el resto
		Integer jobId = entity.getJob().getId();

		if (this.repository.sumPercentageDuty(jobId) != null && !errors.hasErrors("percentageTimeWeek")) {
			Double actualPercent = this.repository.sumPercentageDuty(jobId) + request.getModel().getInteger("percentageTimeWeek");
			over100 = actualPercent <= 100.00;
			errors.state(request, over100, "percentageTimeWeek", "employer.duty.error.over100");
		}

		//TODO: La entidad no se considera SPAM

		Sysconfig sysconfig = this.repository.findSysconfig();
		String[] spam = sysconfig.getSpamwords().split(",");
		Double contador = 0.0;
		Double treshold = sysconfig.getThreshold();
		String[] description = request.getModel().getString("description").split("\\s+");
		if (!errors.hasErrors("description")) {
			for (String a : spam) {
				for (String b : description) {
					if (b.equals(a)) {
						contador += 1;
					}
				}
			}

			Double resultado = contador / description.length;

			if (treshold <= resultado) {
				isSpam = true;
			}

			errors.state(request, !isSpam, "description", "employer.duty.error.isSpam");
		}

	}

	@Override
	public void create(final Request<Duty> request, final Duty entity) {
		assert request != null;
		assert entity != null;

		this.repository.save(entity);

	}
}
