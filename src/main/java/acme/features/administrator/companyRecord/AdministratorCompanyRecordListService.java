
package acme.features.administrator.companyRecord;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.companyRecords.CompanyRecord;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Administrator;
import acme.framework.services.AbstractListService;

@Service
public class AdministratorCompanyRecordListService implements AbstractListService<Administrator, CompanyRecord> {

	@Autowired
	AdministratorCompanyRecordRepository repository;


	@Override
	public boolean authorise(final Request<CompanyRecord> request) {
		assert request != null;

		return true;
	}

	@Override
	public Collection<CompanyRecord> findMany(final Request<CompanyRecord> request) {
		assert request != null;

		Collection<CompanyRecord> result;

		result = this.repository.findManyAll();

		return result;
	}

	@Override
	public void unbind(final Request<CompanyRecord> request, final CompanyRecord entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "companyname", "sector", "activdescription", "rating");
	}

}
