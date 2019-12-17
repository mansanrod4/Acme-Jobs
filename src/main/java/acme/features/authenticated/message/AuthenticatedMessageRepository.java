
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

	@Query("select a from Authenticated a where a.id = ?1")
	Authenticated findAuthenticatedById(int id);

	@Query("select t from Messagethread t where t.id = ?1")
	Messagethread findThreadById(int id);

	@Query("select s from Sysconfig s")
	Sysconfig findspam();
}
