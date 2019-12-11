
package acme.features.employer.duty;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import acme.entities.duties.Duty;
import acme.entities.roles.Employer;
import acme.framework.components.BasicCommand;
import acme.framework.controllers.AbstractController;

@Controller
@RequestMapping("/employer/duty")
public class EmployerDutyController extends AbstractController<Employer, Duty> {

	//Internal state

	@Autowired
	private EmployerDutyListService		listService;

	@Autowired
	private EmployerDutyShowService		showService;

	@Autowired
	private EmployerDutyCreateService	createService;

	@Autowired
	private EmployerDutyDeleteService	deleteService;

	@Autowired
	private EmployerDutyUpdateService	updateService;


	//Constructores

	@PostConstruct
	private void initialise() {
		super.addBasicCommand(BasicCommand.LIST, this.listService);
		super.addBasicCommand(BasicCommand.SHOW, this.showService);
		super.addBasicCommand(BasicCommand.CREATE, this.createService);
		super.addBasicCommand(BasicCommand.DELETE, this.deleteService);
		super.addBasicCommand(BasicCommand.UPDATE, this.updateService);
	}

}
