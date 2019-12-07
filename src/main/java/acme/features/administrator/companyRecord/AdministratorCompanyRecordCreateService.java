
package acme.features.administrator.companyRecord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.companyRecords.CompanyRecord;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Administrator;
import acme.framework.services.AbstractCreateService;

@Service
public class AdministratorCompanyRecordCreateService implements AbstractCreateService<Administrator, CompanyRecord> {

	// Internal State

	@Autowired
	AdministratorCompanyRecordRepository repository;


	// AbstractCreateService<Administrator, CompanyRecord> interface

	@Override
	public boolean authorise(final Request<CompanyRecord> request) {
		assert request != null;

		return true;
	}

	@Override
	public void bind(final Request<CompanyRecord> request, final CompanyRecord entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors);
	}

	@Override
	public void unbind(final Request<CompanyRecord> request, final CompanyRecord entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "companyname", "sector", "ceoname", "activdescription", "website", "phone", "email", "incorporated", "rating");
	}

	@Override
	public CompanyRecord instantiate(final Request<CompanyRecord> request) {
		CompanyRecord result;

		result = new CompanyRecord();

		return result;
	}

	@Override
	public void validate(final Request<CompanyRecord> request, final CompanyRecord entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		String tlfn = entity.getPhone();
		Boolean isMatch = tlfn.matches("^(?:((?:[+][0-9]{1,3})?(?:[ ]{1})?(?:[\\(]{1})?(?:[0-9]{0,4})?(?:[\\)]{1})?[ ]{1})?)([0-9]{6,10})$");
		errors.state(request, isMatch, "phone", "company-record.form.error.phone");

	}

	@Override
	public void create(final Request<CompanyRecord> request, final CompanyRecord entity) {
		this.repository.save(entity);
	}

}
