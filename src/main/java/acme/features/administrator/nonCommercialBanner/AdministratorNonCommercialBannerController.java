
package acme.features.administrator.nonCommercialBanner;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import acme.entities.banners.NonCommercialBanner;
import acme.framework.components.BasicCommand;
import acme.framework.controllers.AbstractController;
import acme.framework.entities.Administrator;

// IMPORTANTE: A pesar de que el RequestMapping es /administrator/banner/commercial-banner/
// los servicios se configuraran en la carpeta views/administrator/commercial-banner/

@Controller
@RequestMapping("/administrator/non-commercial-banner/")
public class AdministratorNonCommercialBannerController extends AbstractController<Administrator, NonCommercialBanner> {

	//Internal state

	@Autowired
	private AdministratorNonCommercialBannerListService		listService;

	@Autowired
	private AdministratorNonCommercialBannerShowService		showService;

	@Autowired
	private AdministratorNonCommercialBannerCreateService	createService;

	@Autowired
	private AdministratorNonCommercialBannerUpdateService	updateService;

	@Autowired
	private AdministratorNonCommercialBannerDeleteService	deleteService;


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
