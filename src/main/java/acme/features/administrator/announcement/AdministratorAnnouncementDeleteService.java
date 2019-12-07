
package acme.features.administrator.announcement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.announcements.Announcement;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Administrator;
import acme.framework.services.AbstractDeleteService;

@Service
public class AdministratorAnnouncementDeleteService implements AbstractDeleteService<Administrator, Announcement> {

	//Internal state

	@Autowired
	AdministratorAnnouncementRepository repository;


	//AbstractUpdateService<Administrator, Announcement> interface

	@Override
	public boolean authorise(final Request<Announcement> request) {
		assert request != null;
		return true;
	}

	@Override
	public void bind(final Request<Announcement> request, final Announcement entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors, "moment");

	}

	@Override
	public void unbind(final Request<Announcement> request, final Announcement entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "title", "text", "moreinfo");
	}

	@Override
	public Announcement findOne(final Request<Announcement> request) {
		assert request != null;
		Announcement result;
		int id;
		id = request.getModel().getInteger("id");
		result = this.repository.findOneAnnouncementById(id);

		return result;
	}

	@Override
	public void validate(final Request<Announcement> request, final Announcement entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
	}

	@Override
	public void delete(final Request<Announcement> request, final Announcement entity) {
		assert request != null;
		assert entity != null;

		this.repository.delete(entity);

	}
}
