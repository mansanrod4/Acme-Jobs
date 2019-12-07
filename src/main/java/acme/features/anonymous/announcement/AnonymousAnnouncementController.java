
package acme.features.anonymous.announcement;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import acme.entities.announcements.Announcement;
import acme.framework.components.BasicCommand;
import acme.framework.controllers.AbstractController;
import acme.framework.entities.Anonymous;

@Controller
@RequestMapping("/anonymous/announcement/")
public class AnonymousAnnouncementController extends AbstractController<Anonymous, Announcement> {

	//Internal state

	@Autowired
	private AnonymousAnnouncementListService	listService;

	@Autowired
	private AnonymousAnnouncementShowService	showService;


	//Constructores

	@PostConstruct
	private void initialise() {
		super.addBasicCommand(BasicCommand.LIST, this.listService);
		super.addBasicCommand(BasicCommand.SHOW, this.showService);
	}

}
