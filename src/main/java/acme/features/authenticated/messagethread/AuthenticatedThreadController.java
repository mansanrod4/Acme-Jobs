
package acme.features.authenticated.messagethread;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import acme.components.CustomCommand;
import acme.entities.messagethread.Messagethread;
import acme.framework.components.BasicCommand;
import acme.framework.controllers.AbstractController;
import acme.framework.entities.Authenticated;

@Controller
@RequestMapping("authenticated/messagethread")
public class AuthenticatedThreadController extends AbstractController<Authenticated, Messagethread> {

	//Internal state

	@Autowired
	private AuthenticatedThreadListMineService	listMineService;

	@Autowired
	private AuthenticatedThreadShowService		showService;


	//Constructores

	@PostConstruct
	private void initialise() {
		super.addBasicCommand(BasicCommand.SHOW, this.showService);
		super.addCustomCommand(CustomCommand.LIST_MINE, BasicCommand.LIST, this.listMineService);

	}

}
