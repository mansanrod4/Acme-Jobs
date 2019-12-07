
package acme.features.worker.applications;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.applications.Application;
import acme.entities.roles.Worker;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractListService;

@Service
public class WorkerApplicationListMineService implements AbstractListService<Worker, Application> {

	@Autowired
	WorkerApplicationRepository repository;


	//AbstractListService<Worker, Application> interface

	@Override
	public boolean authorise(final Request<Application> request) {
		assert request != null;

		return true;
	}

	@Override
	public Collection<Application> findMany(final Request<Application> request) {
		assert request != null;

		Collection<Application> result;
		Principal principal;

		principal = request.getPrincipal();
		result = this.repository.findManyByWorkerId(principal.getActiveRoleId());

		return result;
	}

	@Override
	public void unbind(final Request<Application> request, final Application entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		model.setAttribute("status", entity.getStatus().toString());
		model.setAttribute("job", entity.getJob().getTitle());

		request.unbind(entity, model, "creationMoment", "referenceNumber", "statement");
	}

}
