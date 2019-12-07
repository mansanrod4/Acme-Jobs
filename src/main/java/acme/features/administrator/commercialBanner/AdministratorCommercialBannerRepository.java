
package acme.features.administrator.commercialBanner;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.banners.CommercialBanner;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AdministratorCommercialBannerRepository extends AbstractRepository {

	@Query("Select a from CommercialBanner a where a.id = ?1")
	CommercialBanner findOneById(int id);

	@Query("Select a from CommercialBanner a")
	Collection<CommercialBanner> findManyInvestors();

	@Query("Select a from CommercialBanner a")
	Collection<CommercialBanner> findManyAll();

}
