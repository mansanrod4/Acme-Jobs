
package acme.features.employer.application;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.applications.Application;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface EmployerApplicationRepository extends AbstractRepository {

	@Query("Select a from Application a where a.id = ?1")
	Application findOneApplicationById(int id);

	@Query("Select a from Application a")
	Collection<Application> findManyAll();

	@Query("Select a from Application a where a.job.employer.id = ?1 order by a.job.reference asc, a.status asc, a.creationMoment asc")
	Collection<Application> findManyApplicationsByEmployerOrderingBy(int id);

}
