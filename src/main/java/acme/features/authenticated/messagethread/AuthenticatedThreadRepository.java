
package acme.features.authenticated.messagethread;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.messagethread.Messagethread;
import acme.entities.messagethread.Userthread;
import acme.entities.sysconfig.Sysconfig;
import acme.framework.entities.Authenticated;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuthenticatedThreadRepository extends AbstractRepository {

	@Query("select t from Messagethread t where t.id = ?1")
	Messagethread findOneThreadById(int id);

	@Query("select ut.thread from Userthread ut where ut.authenticated.id = ?1")
	Collection<Messagethread> findManyByAuthenticatedId(int id);

	@Query("select a from Authenticated a where a.id = ?1")
	Authenticated findAuthenticatedById(int id);

	@Query("select ut from Userthread ut where ut.thread.id=?1 and ut.authenticated.id =?2")
	Userthread findOneByThreadIdAndAuthenticatedId(int threadId, int authenticatedId);

	@Query("select ut.authenticated from Userthread ut where ut.thread.id=?1 and ut.creator= true")
	Authenticated findAuthorByThreadId(int threadId);

	@Query("select s from Sysconfig s")
	Sysconfig findspam();

}
