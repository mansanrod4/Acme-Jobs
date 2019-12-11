
package acme.features.authenticated.messagethread;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.messagethread.Messagethread;
import acme.framework.entities.Authenticated;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuthenticatedThreadRepository extends AbstractRepository {

	@Query("Select t from Messagethread t where t.id = ?1")
	Messagethread findOneThreadById(int id);

	@Query("select mt from Messagethread mt inner join mt.users u with u.id=?1")
	Collection<Messagethread> findManyByAuthenticatedId(int id);

	@Query("Select au from Authenticated au where au.id = ?1")
	Authenticated findAuthorById(int id);
}
