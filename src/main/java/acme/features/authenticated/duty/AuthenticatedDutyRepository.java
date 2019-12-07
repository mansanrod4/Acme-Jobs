
package acme.features.authenticated.duty;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.duties.Duty;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuthenticatedDutyRepository extends AbstractRepository {

	@Query("Select d from Duty d where d.id = ?1")
	Duty findOneDutyById(int id);

	@Query("Select d from Duty d")
	Collection<Duty> findManyAll();

	@Query("Select d from Duty d where d.job.id = ?1")
	Collection<Duty> findManyDutiesByJob(int id);

}
