
package acme.features.authenticated.authenticated;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import acme.components.CustomCommand;
import acme.framework.components.BasicCommand;
import acme.framework.controllers.AbstractController;
import acme.framework.entities.Authenticated;

@Controller
@RequestMapping("/authenticated/authenticated/")
public class AuthenticatedAuthenticatedController extends AbstractController<Authenticated, Authenticated> {

	@Autowired
	private AuthenticatedAuthenticatedListService listService;


	@PostConstruct
	private void initialise() {
		super.addCustomCommand(CustomCommand.LIST_NON_INCLUDED, BasicCommand.LIST, this.listService);
	}
}
