
package acme.features.sponsor.commercialBanner;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.banners.CommercialBanner;
import acme.entities.roles.Sponsor;
import acme.entities.sysconfig.Sysconfig;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface SponsorCommercialBannerRepository extends AbstractRepository {

	@Query("Select j from CommercialBanner j where j.id = ?1")
	CommercialBanner findOneCommercialBannerById(int id);

	@Query("Select j from CommercialBanner j where j.sponsor.id = ?1")
	Collection<CommercialBanner> findManyByCommercialBannerId(int sponsorId);

	@Query("Select s from Sponsor s where s.id = ?1")
	Sponsor findSponsorById(int id);

	@Query("Select s from Sysconfig s")
	Sysconfig findSysconfig();
}
