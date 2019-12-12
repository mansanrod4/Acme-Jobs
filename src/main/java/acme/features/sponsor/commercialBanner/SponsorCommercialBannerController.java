
package acme.features.sponsor.commercialBanner;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import acme.entities.banners.CommercialBanner;
import acme.entities.roles.Sponsor;
import acme.framework.components.BasicCommand;
import acme.framework.controllers.AbstractController;

@Controller
@RequestMapping("/sponsor/commercial-banner/")
public class SponsorCommercialBannerController extends AbstractController<Sponsor, CommercialBanner> {

	//Internal state

	@Autowired
	private SponsorCommercialBannerListService		listService;

	@Autowired
	private SponsorCommercialBannerShowService		showService;

	@Autowired
	private SponsorCommercialBannerCreateService	createService;

	@Autowired
	private SponsorCommercialBannerUpdateService	updateService;

	@Autowired
	private SponsorCommercialBannerDeleteService	deleteService;


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
