
package acme.features.authenticated.announcement;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.announcements.Announcement;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuthenticatedAnnouncementRepository extends AbstractRepository {

	@Query("Select a from Announcement a where a.id = ?1")
	Announcement findOneById(int id);

	@Query("Select a from Announcement a")
	Collection<Announcement> findManyAll();

	@Query("Select a from Announcement a where a.moment >= ?1")
	Collection<Announcement> findSomeAll(Date substractedDate);

}
