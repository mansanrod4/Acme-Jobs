
package acme.features.anonymous.companyRecord;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import acme.entities.companyRecords.CompanyRecord;
import acme.framework.components.BasicCommand;
import acme.framework.controllers.AbstractController;
import acme.framework.entities.Anonymous;

@Controller
@RequestMapping("/anonymous/company-record/")
public class AnonymousCompanyRecordController extends AbstractController<Anonymous, CompanyRecord> {

	//Internal state

	@Autowired
	private AnonymousCompanyRecordListService	listService;

	@Autowired
	private AnonymousCompanyRecordShowService	showService;


	//Constructores

	@PostConstruct
	private void initialise() {
		super.addBasicCommand(BasicCommand.LIST, this.listService);
		super.addBasicCommand(BasicCommand.SHOW, this.showService);
	}

}
