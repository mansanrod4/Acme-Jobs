
package acme.features.employer.duty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.duties.Duty;
import acme.entities.roles.Employer;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractDeleteService;

@Service
public class EmployerDutyDeleteService implements AbstractDeleteService<Employer, Duty> {

	//Internal state

	@Autowired
	EmployerDutyRepository repository;


	//AbstractUpdateService<Employer, Job> interface

	@Override
	public boolean authorise(final Request<Duty> request) {
		assert request != null;

		//Para que otro employer no pueda borrar el duty
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

		request.unbind(entity, model, "title", "percentageTimeWeek", "description", "job");
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
	}

	@Override
	public void delete(final Request<Duty> request, final Duty entity) {
		assert request != null;
		assert entity != null;

		this.repository.delete(entity);

	}
}
