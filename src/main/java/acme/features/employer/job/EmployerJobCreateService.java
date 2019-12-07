
package acme.features.employer.job;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.jobs.Job;
import acme.entities.roles.Employer;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractCreateService;

@Service
public class EmployerJobCreateService implements AbstractCreateService<Employer, Job> {

	//Internal state

	@Autowired
	EmployerJobRepository repository;


	@Override
	public boolean authorise(final Request<Job> request) {
		assert request != null;
		return true;
	}

	@Override
	public void bind(final Request<Job> request, final Job entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		Employer employer = this.repository.findOneEmployerById(request.getPrincipal().getActiveRoleId());

		entity.setEmployer(employer);

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
	public Job instantiate(final Request<Job> request) {
		Job result;
		result = new Job();

		return result;
	}

	@Override
	public void validate(final Request<Job> request, final Job entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		//Validaciones

		boolean isOneWeekLater;

		//DEADLINE MAYOR A UNA SEMANA DESDE AHORA
		if (!errors.hasErrors("deadLine")) {									//Si no hay errores:

			LocalDateTime now = LocalDateTime.now();							//Obtenemos la fecha actual
			LocalDateTime nowplus7 = now.plus(7, ChronoUnit.DAYS);				//Le sumamos una semana
			Date date = Timestamp.valueOf(nowplus7);
			Date date1 = request.getModel().getDate("deadLine");				//Obtenemos la fecha insertada

			isOneWeekLater = date1.after(date);									//Comparamos fechas
			errors.state(request, isOneWeekLater, "deadLine", "employer.job.error.deadline");
		}

		//TODO: Salario en euros?

		//TODO: Ticker duplicado?

	}

	@Override
	public void create(final Request<Job> request, final Job entity) {
		assert request != null;
		assert entity != null;

		Employer employer = this.repository.findOneEmployerById(request.getPrincipal().getActiveRoleId());
		entity.setEmployer(employer);

		this.repository.save(entity);

	}
}
