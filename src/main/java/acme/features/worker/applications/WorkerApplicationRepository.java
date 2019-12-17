
package acme.features.worker.applications;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.applications.Application;
import acme.entities.jobs.Job;
import acme.entities.roles.Worker;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface WorkerApplicationRepository extends AbstractRepository {

	@Query("Select a from Application a where a.id = ?1")
	Application findOneApplicationById(int id);

	@Query("Select a from Application a where a.worker.id = ?1")
	Collection<Application> findManyByWorkerId(int workerId);

	@Query("Select a from Application a where a.job.id = ?1")
	Collection<Application> findManyByJobId(int jobId);

	@Query("Select w from Worker w where w.id = ?1")
	Worker findWorkerById(int workerId);

	@Query("Select j from Job j where j.id = ?1")
	Job findJobById(int id);

}
