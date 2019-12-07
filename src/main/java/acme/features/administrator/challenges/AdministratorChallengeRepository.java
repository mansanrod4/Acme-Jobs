
package acme.features.administrator.challenges;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.challenges.Challenge;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AdministratorChallengeRepository extends AbstractRepository {

	@Query("Select c from Challenge c where c.id = ?1")
	Challenge findOneById(int id);

	@Query("Select c from Challenge c")
	Collection<Challenge> findManyAll();
}
