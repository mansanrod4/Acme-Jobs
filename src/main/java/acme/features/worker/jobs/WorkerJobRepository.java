
package acme.features.worker.jobs;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.applications.Application;
import acme.entities.jobs.Job;
import acme.entities.roles.Worker;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface WorkerJobRepository extends AbstractRepository {

	@Query("Select j from Job j where j.id = ?1")
	Job findOneJobById(int id);

	@Query("Select j from Job j where j.finalMode = ?1")
	Collection<Job> findManyJobByStatus(boolean finalMode);

	@Query("Select w from Worker w where w.id = ?1")
	Worker findWorkerById(int workerId);

	@Query("Select a from Application a where a.worker.id = ?1")
	Collection<Application> findManyByWorkerId(int workerId);

}
