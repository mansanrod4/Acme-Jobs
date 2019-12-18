
package acme.features.authenticated.message;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.message.Message;
import acme.entities.messagethread.Messagethread;
import acme.entities.sysconfig.Sysconfig;
import acme.framework.entities.Authenticated;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuthenticatedMessageRepository extends AbstractRepository {

	@Query("select a from Message a where a.id = ?1")
	Message findOneById(int id);

	@Query("select t.messages from Messagethread t where t.id = ?1")
	Collection<Message> findManyByMessagethread(int id);

	@Query("select t.id from Messagethread t join t.messages m where m.id = ?1")
	int findThreadIdByMessageId(int id);

	@Query("select a from Authenticated a where a.id = ?1")
	Authenticated findAuthenticatedById(int id);

	@Query("select t from Messagethread t where t.id = ?1")
	Messagethread findThreadById(int id);

	@Query("select s from Sysconfig s")
	Sysconfig findspam();

	@Query("select count(ut) from Userthread ut where ut.authenticated.id = ?1 and ut.thread.id = ?2")
	int countUserThreadByAuIdAndThreadId(int authenticatedId, int idThread);
}
