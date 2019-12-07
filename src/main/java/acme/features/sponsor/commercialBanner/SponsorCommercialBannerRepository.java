
package acme.features.sponsor.commercialBanner;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.banners.CommercialBanner;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface SponsorCommercialBannerRepository extends AbstractRepository {

	@Query("Select j from CommercialBanner j where j.id = ?1")
	CommercialBanner findOneCommercialBannerById(int id);

	@Query("Select j from CommercialBanner j where j.sponsor.id = ?1")
	Collection<CommercialBanner> findManyByCommercialBannerId(int sponsorId);
}
