
package acme.features.auditor.audited;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.Audits.Audit;
import acme.entities.jobs.Job;
import acme.entities.roles.Auditor;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuditorAuditedRepository extends AbstractRepository {

	@Query("Select d from Audit d where d.id = ?1")
	Audit findOneAuditRecordById(int id);

	@Query("Select d from Audit d")
	Collection<Audit> findManyAll();

	@Query("Select d from Audit d where d.job.id = ?1")
	Collection<Audit> findManyAuditRecordsByJob(int job_id);

	@Query("Select d from Auditor d where d.id = ?1")
	Auditor findAuditorbyId(int auditor_id);

	@Query("Select d from Job d where d.id = ?1")
	Job findJobById(int job_id);

}
