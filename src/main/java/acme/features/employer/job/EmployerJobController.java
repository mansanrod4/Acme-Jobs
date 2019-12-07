
package acme.features.employer.job;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import acme.components.CustomCommand;
import acme.entities.jobs.Job;
import acme.entities.roles.Employer;
import acme.framework.components.BasicCommand;
import acme.framework.controllers.AbstractController;

@Controller
@RequestMapping("/employer/job/")
public class EmployerJobController extends AbstractController<Employer, Job> {

	//Internal state

	@Autowired
	private EmployerJobListMineService			listMineService;

	@Autowired
	private EmployerJobShowService				showService;

	@Autowired
	private EmployerJobCreateService			createService;

	@Autowired
	private EmployerJobCreateAndPublishService	createAndPublishService;

	@Autowired
	private EmployerJobUpdateService			updateService;

	@Autowired
	private EmployerJobPublishService			publishService;


	//Constructores

	@PostConstruct
	private void initialise() {
		super.addBasicCommand(BasicCommand.CREATE, this.createService);
		super.addBasicCommand(BasicCommand.SHOW, this.showService);
		super.addBasicCommand(BasicCommand.UPDATE, this.updateService);
		super.addCustomCommand(CustomCommand.LIST_MINE, BasicCommand.LIST, this.listMineService);
		super.addCustomCommand(CustomCommand.CREATE_AND_PUBLISH, BasicCommand.CREATE, this.createAndPublishService);
		super.addCustomCommand(CustomCommand.PUBLISH, BasicCommand.UPDATE, this.publishService);
	}

}
