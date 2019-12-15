
package acme.features.auditor.audited;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Audits.Audit;
import acme.entities.Audits.Status;
import acme.entities.roles.Auditor;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractListService;

@Service
public class AuditorAuditedListService implements AbstractListService<Auditor, Audit> {

	//Internal State

	@Autowired
	AuditorAuditedRepository repository;


	//AbstractListService<Auditor, AuditRecord> interface

	@Override
	public boolean authorise(final Request<Audit> request) {
		assert request != null;

		return true;
	}

	@Override
	public void unbind(final Request<Audit> request, final Audit entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		model.setAttribute("identity.name", entity.getAuditor().getUserAccount().getUsername());

		if (entity.getAuditor().getUserAccount().getId() == request.getPrincipal().getAccountId()) {
			model.setAttribute("me", " * ");
		} else {
			model.setAttribute("me", " ");
		}

		request.unbind(entity, model, "title", "moment", "status");
	}

	@Override
	public Collection<Audit> findMany(final Request<Audit> request) {
		assert request != null;

		Collection<Audit> result = new ArrayList<Audit>();

		Integer jobId = request.getModel().getInteger("job_id");

		for (Audit audit : this.repository.findManyAuditRecordsByJob(jobId)) {
			Audit a = this.repository.findOneAuditRecordById(audit.getId());
			if (a.getStatus() == Status.PUBLISHED || a.getAuditor().getUserAccount().getId() == request.getPrincipal().getAccountId()) {
				result.add(a);
			}
		}

		return result;
	}
}
