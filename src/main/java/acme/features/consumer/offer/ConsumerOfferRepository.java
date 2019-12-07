
package acme.features.consumer.offer;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.offers.Offer;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface ConsumerOfferRepository extends AbstractRepository {

	@Query("Select o from Offer o where o.id = ?1")
	Offer findOneById(int id);

	@Query("Select o from Offer o")
	Collection<Offer> findManyAll();

	@Query("Select o from Offer o where o.id = ?1")
	Offer findOneOfferById(int id);

	@Query("Select o from Offer o where o.ticker = ?1")
	Offer findOneOfferByTicker(String ticker);

	@Query("Select o from Offer o")
	Collection<Offer> findManyOffers();

}
