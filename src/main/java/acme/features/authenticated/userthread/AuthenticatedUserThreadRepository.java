
package acme.features.authenticated.userthread;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.messagethread.Messagethread;
import acme.entities.messagethread.Userthread;
import acme.framework.entities.Authenticated;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuthenticatedUserThreadRepository extends AbstractRepository {

	@Query("select t from Messagethread t where t.id = ?1")
	Messagethread findOneById(int id);

	@Query("select ut from Userthread ut where ut.thread.id = ?1")
	Collection<Userthread> findUserThreadsInThread(int threadId);

	@Query("select ut from Userthread ut where ut.id = ?1")
	Userthread findOneUserThreadById(int id);

	@Query("select ut.thread from Userthread ut where ut.id = ?1")
	Messagethread findThreadByUserThreadId(int id);

	@Query("select a from Authenticated a where a.id = ?1")
	Authenticated findOneAuthenticatedById(int id);

	@Query("select ut from Userthread ut where ut.thread.id=?1 and ut.authenticated.id =?2")
	Userthread findOneByThreadIdAndAuthenticatedId(int threadId, int authenticatedId);

}
