
package acme.features.auditor.audited;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Audits.Audit;
import acme.entities.roles.Auditor;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractShowService;

@Service
public class AuditorAuditedShowService implements AbstractShowService<Auditor, Audit> {

	//Internal State

	@Autowired
	private AuditorAuditedRepository repository;


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

		request.unbind(entity, model, "title", "moment", "body", "status");
	}

	@Override
	public Audit findOne(final Request<Audit> request) {
		assert request != null;

		Audit result;
		int id;

		id = request.getModel().getInteger("id");
		result = this.repository.findOneAuditRecordById(id);

		return result;
	}

}
