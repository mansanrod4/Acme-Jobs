
package acme.features.provider.requests;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.requests.Request;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface ProviderRequestRepository extends AbstractRepository {

	@Query("Select r from Request r where r.id = ?1")
	Request findOneById(int id);

	@Query("Select r from Request r")
	Collection<Request> findManyAll();

	@Query("Select r from Request r where r.id = ?1")
	Request findOneRequestById(int id);

	@Query("Select r from Request r where r.ticker = ?1")
	Request findOneRequestByTicker(String ticker);

	@Query("Select r from Request r")
	Collection<Request> findManyRequests();

}
