
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
import acme.framework.services.AbstractUpdateService;

@Service
public class EmployerJobPublishService implements AbstractUpdateService<Employer, Job> {

	//Internal state

	@Autowired
	EmployerJobRepository repository;


	//AbstractUpdateService<Administrator, Announcement> interface

	@Override
	public boolean authorise(final Request<Job> request) {
		assert request != null;

		//Validacion

		boolean isFinalMode;

		//A JOB CAN BE MODIFIED AS LONG AS IT'S NOT SAVED IN FINAL MODE
		isFinalMode = this.repository.findOneJobById(request.getModel().getInteger("id")).isFinalMode();

		return !isFinalMode;
	}

	@Override
	public void bind(final Request<Job> request, final Job entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors, "finalMode");
	}

	@Override
	public void unbind(final Request<Job> request, final Job entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model);
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

		Integer jobId = request.getModel().getInteger("id");
		boolean hasDescriptor, isOneWeekLater, duties100, hasNoDuties, isEuroZone = false;

		//DEADLINE MAYOR A UNA SEMANA DESDE AHORA
		if (!errors.hasErrors("deadLine")) {									//Si no hay errores:

			LocalDateTime now = LocalDateTime.now();							//Obtenemos la fecha actual
			LocalDateTime nowplus7 = now.plus(7, ChronoUnit.DAYS);				//Le sumamos una semana
			Date date = Timestamp.valueOf(nowplus7);
			Date date1 = request.getModel().getDate("deadLine");				//Obtenemos la fecha insertada

			isOneWeekLater = date1.after(date);									//Comparamos fechas
			errors.state(request, isOneWeekLater, "deadLine", "employer.job.error.deadline");
		}

		//No se puede publicar si no tiene descripción (DESCRIPTOR)
		hasDescriptor = request.getModel().getString("description").isEmpty();
		errors.state(request, !hasDescriptor, "description", "employer.job.error.hasDescriptor");

		//No se puede publicar si no tiene duties (DESCRIPTOR) -Solo al Publicar-
		hasNoDuties = this.repository.findManyDutiesByJob(jobId).isEmpty();
		errors.state(request, !hasNoDuties, "description", "employer.job.error.noDuties");

		//La suma de los porcentajes de los duties debe ser del 100% -Solo al Publicar-
		if (this.repository.sumPercentageDuty(jobId) != null) {
			duties100 = this.repository.sumPercentageDuty(jobId).equals(100.00);
			errors.state(request, duties100, "description", "employer.job.error.dutiesOver100");
		}

		//Salario en euros
		if (!errors.hasErrors("salary")) {
			String eur2 = "€", eur = "EUR", currency = request.getModel().getAttribute("salary").toString();
			if (currency.contains(eur) || currency.contains(eur2)) {
				isEuroZone = true;
			}
			errors.state(request, isEuroZone, "salary", "employer.job.error.money-no-euro");
		}

		//TODO: La entidad no se considera SPAM
	}

	@Override
	public void update(final Request<Job> request, final Job entity) {
		assert request != null;
		assert entity != null;
		entity.setFinalMode(true);

		this.repository.save(entity);

	}
}
