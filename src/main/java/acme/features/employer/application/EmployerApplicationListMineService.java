
package acme.features.employer.application;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.applications.Application;
import acme.entities.roles.Employer;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractListService;

@Service
public class EmployerApplicationListMineService implements AbstractListService<Employer, Application> {

	@Autowired
	EmployerApplicationRepository repository;


	@Override
	public boolean authorise(final Request<Application> request) {
		assert request != null;

		return true;
	}

	@Override
	public Collection<Application> findMany(final Request<Application> request) {
		assert request != null;

		Collection<Application> result;

		Integer employerId = request.getPrincipal().getActiveRoleId();
		result = this.repository.findManyApplicationsByEmployerOrderingBy(employerId);

		return result;
	}

	@Override
	public void unbind(final Request<Application> request, final Application entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		model.setAttribute("status", entity.getStatus().toString());
		model.setAttribute("jobReference", entity.getJob().getReference());

		request.unbind(entity, model, "creationMoment", "referenceNumber", "statement");
	}

}
