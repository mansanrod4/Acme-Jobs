
package acme.features.employer.job;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.applications.Application;
import acme.entities.duties.Duty;
import acme.entities.jobs.Job;
import acme.entities.roles.Employer;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface EmployerJobRepository extends AbstractRepository {

	@Query("Select j from Job j where j.id = ?1")
	Job findOneJobById(int id);

	@Query("Select j from Job j where j.employer.id = ?1")
	Collection<Job> findManyByEmployerId(int employerId);

	@Query("Select e from Employer e where e.id = ?1")
	Employer findOneEmployerById(int id);

	@Query("Select a from Application a")
	Collection<Application> findManyApplication();

	@Query("Select d from Duty d where d.job.id = ?1")
	Collection<Duty> findManyDutiesByJob(int jobId);

	@Query("Select sum(d.percentageTimeWeek) from Duty d where d.job.id = ?1")
	Double sumPercentageDuty(int jobId);
}
