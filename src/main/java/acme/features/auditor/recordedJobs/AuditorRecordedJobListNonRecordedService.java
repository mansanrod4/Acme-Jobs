
package acme.features.auditor.recordedJobs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Audits.Audit;
import acme.entities.jobs.Job;
import acme.entities.roles.Auditor;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractListService;

@Service
public class AuditorRecordedJobListNonRecordedService implements AbstractListService<Auditor, Job> {

	@Autowired
	AuditorRecordedJobRepository repository;


	//AbstractListService<Employer, Job> interface

	@Override
	public boolean authorise(final Request<Job> request) {
		assert request != null;

		return true;
	}

	@Override
	public Collection<Job> findMany(final Request<Job> request) {
		assert request != null;

		Collection<Job> result;
		List<Job> jobs = new ArrayList<>();
		Principal principal = request.getPrincipal();

		Collection<Audit> allRecords = this.repository.findManyAllAuditRecord();

		for (Audit ar : allRecords) {
			if (ar.getAuditor().getUserAccount().getId() != principal.getAccountId() && !jobs.contains(this.repository.findOneJobById(ar.getJob().getId()))) {
				jobs.add(this.repository.findOneJobById(ar.getJob().getId()));
			}
		}

		result = jobs;

		return result;
	}

	@Override
	public void unbind(final Request<Job> request, final Job entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "reference", "title", "deadLine");
	}

}
