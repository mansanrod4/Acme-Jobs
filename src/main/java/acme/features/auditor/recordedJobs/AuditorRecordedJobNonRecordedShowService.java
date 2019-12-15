
package acme.features.auditor.recordedJobs;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Audits.Audit;
import acme.entities.jobs.Job;
import acme.entities.roles.Auditor;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractShowService;

@Service
public class AuditorRecordedJobNonRecordedShowService implements AbstractShowService<Auditor, Job> {

	//Internal State

	@Autowired
	private AuditorRecordedJobRepository repository;


	//AbstractListService<Employer, Job> interface

	@Override
	public boolean authorise(final Request<Job> request) {
		assert request != null;
		boolean result;
		int jobId;
		Job job;
		Principal principal = request.getPrincipal();

		jobId = request.getModel().getInteger("id");
		job = this.repository.findOneJobById(jobId);

		Collection<Audit> allRecords = this.repository.findManyAllAuditRecord();
		boolean hasPrincipalRecordedThisJob = false;

		for (Audit ar : allRecords) {
			if (ar.getAuditor().getUserAccount().getId() == principal.getAccountId() && ar.getJob().getId() == jobId) {
				hasPrincipalRecordedThisJob = true;
			}
		}
		result = job.isFinalMode() || !job.isFinalMode() && hasPrincipalRecordedThisJob;

		return result;
		//		return true;
	}

	@Override
	public void unbind(final Request<Job> request, final Job entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "reference", "title", "deadLine");
		request.unbind(entity, model, "salary", "moreInfo", "description", "finalMode");
	}

	@Override
	public Job findOne(final Request<Job> request) {
		assert request != null;

		Job result;
		int id;

		id = request.getModel().getInteger("id");
		result = this.repository.findOneJobById(id);

		return result;
	}

}
