
package acme.features.administrator.investor;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import acme.entities.investor.Investor;
import acme.framework.components.BasicCommand;
import acme.framework.controllers.AbstractController;
import acme.framework.entities.Administrator;

@Controller
@RequestMapping("/administrator/investor/")
public class AdministratorInvestorController extends AbstractController<Administrator, Investor> {

	//Internal state

	@Autowired
	private AdministratorInvestorListService	listService;

	@Autowired
	private AdministratorInvestorShowService	showService;

	@Autowired
	private AdministratorInvestorCreateService	createService;

	@Autowired
	private AdministratorInvestorUpdateService	updateService;

	@Autowired
	private AdministratorInvestorDeleteService	deleteService;


	//Constructores

	@PostConstruct
	private void initialise() {
		super.addBasicCommand(BasicCommand.LIST, this.listService);
		super.addBasicCommand(BasicCommand.SHOW, this.showService);
		super.addBasicCommand(BasicCommand.CREATE, this.createService);
		super.addBasicCommand(BasicCommand.UPDATE, this.updateService);
		super.addBasicCommand(BasicCommand.DELETE, this.deleteService);
	}

}
