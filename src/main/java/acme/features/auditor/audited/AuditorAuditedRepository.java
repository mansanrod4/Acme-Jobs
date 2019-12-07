
package acme.features.auditor.audited;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.Audits.Audit;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuditorAuditedRepository extends AbstractRepository {

	@Query("Select d from Audit d where d.id = ?1")
	Audit findOneAuditRecordById(int id);

	@Query("Select d from Audit d")
	Collection<Audit> findManyAll();

	@Query("Select d from Audit d where d.job.id = ?1")
	Collection<Audit> findManyAuditRecordsByJob(int job_id);

}
