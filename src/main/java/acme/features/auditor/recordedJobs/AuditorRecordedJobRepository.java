
package acme.features.auditor.recordedJobs;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.Audits.Audit;
import acme.entities.jobs.Job;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuditorRecordedJobRepository extends AbstractRepository {

	@Query("Select j from Job j where j.id = ?1")
	Job findOneJobById(int id);

	@Query("Select ar from Audit ar")
	Collection<Audit> findManyAllAuditRecord();

	@Query("Select ar from Audit ar where ar.auditor.id = ?1")
	Collection<Audit> findManyAuditRecordByAuditorId(int auditorId);

	@Query("Select count(d) from Audit d where d.auditor.id = ?1 and d.job.id = ?2")
	int countAuditedJobByAuditorAndJob(int auditor_id, int job_id);

}
