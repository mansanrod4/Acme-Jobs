
package acme.features.auditor.recordedJobs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.jobs.Job;
import acme.entities.roles.Auditor;
import acme.framework.components.Model;
import acme.framework.components.Request;
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

		List<Job> recordedByThisAuditor = new ArrayList<Job>();
		List<Job> result = new ArrayList<Job>();
		Integer auditorId = request.getPrincipal().getActiveRoleId(); //auditorId

		for (Integer i : this.repository.findJobAuditsId(auditorId)) {
			Job j = this.repository.findOneJobById(i);
			recordedByThisAuditor.add(j); //Trabajos con audiciones del auditorId
		}

		for (Job j : this.repository.findManyAllJob()) {
			result.add(j); //Todos los trabajos
		}

		result.removeAll(recordedByThisAuditor); //Quito los trabajos que tenga audiciones del auditorId

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
