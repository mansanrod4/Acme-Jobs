
package acme.features.auditor.audited;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Audits.Audit;
import acme.entities.jobs.Job;
import acme.entities.roles.Auditor;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractCreateService;

@Service
public class AuditorAuditedCreateService implements AbstractCreateService<Auditor, Audit> {

	//Internal state

	@Autowired
	AuditorAuditedRepository repository;

	//AbstractUpdateService<Auditor, Audit> interface


	@Override
	public boolean authorise(final Request<Audit> request) {
		assert request != null;
		return true;
	}

	@Override
	public void bind(final Request<Audit> request, final Audit entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		Date moment;

		moment = new Date(System.currentTimeMillis() - 1);
		entity.setMoment(moment);

		request.bind(entity, errors, "moment");
	}

	@Override
	public void unbind(final Request<Audit> request, final Audit entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		int job_id = request.getModel().getInteger("job_id");
		model.setAttribute("job_id", job_id);

		request.unbind(entity, model, "title", "moment", "body", "status");
	}

	@Override
	public Audit instantiate(final Request<Audit> request) {

		Audit result;

		result = new Audit();

		int job_id = request.getModel().getInteger("job_id");
		Job job = this.repository.findJobById(job_id);
		result.setJob(job);

		int auditor_id = request.getPrincipal().getActiveRoleId();
		Auditor auditor = this.repository.findAuditorById(auditor_id);
		result.setAuditor(auditor);

		return result;
	}

	@Override
	public void validate(final Request<Audit> request, final Audit entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
	}

	@Override
	public void create(final Request<Audit> request, final Audit entity) {
		Date moment;

		moment = new Date(System.currentTimeMillis() - 1);
		entity.setMoment(moment);

		this.repository.save(entity);
	}

}
