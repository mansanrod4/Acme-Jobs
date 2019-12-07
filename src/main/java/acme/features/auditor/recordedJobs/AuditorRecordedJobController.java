
package acme.features.auditor.recordedJobs;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import acme.components.CustomCommand;
import acme.entities.jobs.Job;
import acme.entities.roles.Auditor;
import acme.framework.components.BasicCommand;
import acme.framework.controllers.AbstractController;

@Controller
@RequestMapping("/auditor/job/")
public class AuditorRecordedJobController extends AbstractController<Auditor, Job> {

	//Internal state

	@Autowired
	private AuditorRecordedJobListRecordedService		listRecordedService;

	@Autowired
	private AuditorRecordedJobListNonRecordedService	listNonRecordedService;

	@Autowired
	private AuditorRecordedJobRecordedShowService		showRecordedService;

	@Autowired
	private AuditorRecordedJobNonRecordedShowService	showNonRecordedService;


	//Constructores

	@PostConstruct
	private void initialise() {
		super.addBasicCommand(BasicCommand.SHOW, this.showRecordedService);
		super.addCustomCommand(CustomCommand.LIST_RECORDED, BasicCommand.LIST, this.listRecordedService);
		super.addCustomCommand(CustomCommand.LIST_NONRECORDED, BasicCommand.LIST, this.listNonRecordedService);
		// Ver como mostrar un show custom
		super.addCustomCommand(CustomCommand.SHOW_RECORDED, BasicCommand.SHOW, this.showRecordedService);
		super.addCustomCommand(CustomCommand.SHOW_NONRECORDED, BasicCommand.SHOW, this.showNonRecordedService);
	}

}
