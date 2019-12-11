
package acme.features.employer.duty;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.duties.Duty;
import acme.entities.jobs.Job;
import acme.entities.sysconfig.Sysconfig;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface EmployerDutyRepository extends AbstractRepository {

	@Query("Select d from Duty d where d.id = ?1")
	Duty findOneDutyById(int id);

	@Query("Select d from Duty d")
	Collection<Duty> findManyAll();

	@Query("Select d from Duty d where d.job.id = ?1")
	Collection<Duty> findManyDutiesByJob(int id);

	@Query("Select j from Job j where j.id = ?1")
	Job findJobById(int id);

	@Query("Select sum(d.percentageTimeWeek) from Duty d where d.job.id = ?1")
	Double sumPercentageDuty(int jobId);

	@Query("Select s from Sysconfig s")
	Sysconfig findSysconfig();

}
