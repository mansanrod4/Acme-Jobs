
package acme.features.authenticated.offer;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.offers.Offer;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuthenticatedOfferRepository extends AbstractRepository {

	@Query("Select a from Offer a where a.id = ?1")
	Offer findOneById(int id);

	@Query("Select a from Offer a")
	Collection<Offer> findManyAll();

}
