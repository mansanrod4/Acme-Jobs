
package acme.features.sponsor.nonCommercialBanner;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.banners.NonCommercialBanner;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface SponsorNonCommercialBannerRepository extends AbstractRepository {

	@Query("Select j from NonCommercialBanner j where j.id = ?1")
	NonCommercialBanner findOneNonCommercialBannerById(int id);

	@Query("Select j from NonCommercialBanner j where j.sponsor.id = ?1")
	Collection<NonCommercialBanner> findManyByNonCommercialBannerId(int sponsorId);
}
