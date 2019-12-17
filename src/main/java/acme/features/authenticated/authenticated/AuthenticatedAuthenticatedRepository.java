
package acme.features.authenticated.authenticated;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.messagethread.Userthread;
import acme.framework.entities.Authenticated;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuthenticatedAuthenticatedRepository extends AbstractRepository {

	@Query("select a from Authenticated a where a not in (select ut.authenticated from Userthread ut where ut.thread.id = ?1)")
	Collection<Authenticated> findUserThreadNotInThread(int threadId);

	@Query("select a from Authenticated a where a.id = ?1")
	Authenticated findOneById(int id);

	@Query("select ut from Userthread ut where ut.thread.id=?1 and ut.authenticated.id =?2")
	Userthread findOneByThreadIdAndAuthenticatedId(int threadId, int authenticatedId);
}
