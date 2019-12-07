
package acme.features.worker.applications;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.applications.Application;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface WorkerApplicationRepository extends AbstractRepository {

	@Query("Select a from Application a where a.id = ?1")
	Application findOneApplicationById(int id);

	@Query("Select a from Application a where a.worker.id = ?1")
	Collection<Application> findManyByWorkerId(int workerId);

}
