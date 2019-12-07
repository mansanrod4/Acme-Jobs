
package acme.features.anonymous.topCompanyRecord;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.companyRecords.CompanyRecord;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AnonymousTopCompanyRecordRepository extends AbstractRepository {

	@Query("Select c from CompanyRecord c where c.id = ?1")
	CompanyRecord findOneById(int id);

	@Query("Select c from CompanyRecord c")
	Collection<CompanyRecord> findManyAll();

	@Query("Select c from CompanyRecord c where c.rating = ?1")
	Collection<CompanyRecord> findFiveStar(Integer rating);

}
