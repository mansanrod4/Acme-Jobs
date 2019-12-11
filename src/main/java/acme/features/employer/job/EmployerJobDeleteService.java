
package acme.features.employer.job;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.applications.Application;
import acme.entities.duties.Duty;
import acme.entities.jobs.Job;
import acme.entities.roles.Employer;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractDeleteService;

@Service
public class EmployerJobDeleteService implements AbstractDeleteService<Employer, Job> {

	//Internal state

	@Autowired
	EmployerJobRepository repository;


	//AbstractUpdateService<Employer, Job> interface

	@Override
	public boolean authorise(final Request<Job> request) {
		assert request != null;

		//A JOB CAN BE DELETED AS LONG AS NO WORKER HAS APPLIED FOR IT

		boolean hasApplications = true;
		Integer jobId = request.getModel().getInteger("id");

		for (Application a : this.repository.findManyApplication()) {
			Integer jobIdApplication = a.getJob().getId();
			if (jobId.equals(jobIdApplication)) {
				hasApplications = false;
			}
		}

		return hasApplications;
	}

	@Override
	public void bind(final Request<Job> request, final Job entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors);

	}

	@Override
	public void unbind(final Request<Job> request, final Job entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "reference", "title", "deadLine", "salary", "moreInfo", "finalMode", "description");
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

	@Override
	public void validate(final Request<Job> request, final Job entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

	}

	@Override
	public void delete(final Request<Job> request, final Job entity) {
		assert request != null;
		assert entity != null;

		//Borramos las tareas del empleo para que no salte error de dependencia
		Integer jobId = entity.getId();
		Collection<Duty> duties = this.repository.findManyDutiesByJob(jobId);
		this.repository.deleteAll(duties);

		//Borramos el empleo
		this.repository.delete(entity);

	}
}
