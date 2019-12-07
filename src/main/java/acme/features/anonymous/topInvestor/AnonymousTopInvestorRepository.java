
package acme.features.anonymous.topInvestor;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.investor.Investor;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AnonymousTopInvestorRepository extends AbstractRepository {

	@Query("Select i from Investor i where i.id = ?1")
	Investor findOneById(int id);

	@Query("Select i from Investor i")
	Collection<Investor> findManyAll();

	@Query("Select i from Investor i where i.star = ?1")
	Collection<Investor> findFiveStar(Integer rating);
}
