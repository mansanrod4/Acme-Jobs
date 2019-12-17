
package acme.features.administrator.dashboard;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.companyRecords.CompanyRecord;
import acme.entities.dashboard.Dashboard;
import acme.entities.investor.Investor;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Administrator;
import acme.framework.services.AbstractListService;

@Service
public class AdministratorDashboardListService implements AbstractListService<Administrator, Dashboard> {

	@Autowired
	AdministratorDashboardRepository repository;


	//AbstractListService<Administrator, Dashboard> interface

	@Override
	public boolean authorise(final Request<Dashboard> request) {
		assert request != null;

		return true;
	}

	@Override
	public Collection<Dashboard> findMany(final Request<Dashboard> request) {
		assert request != null;

		Collection<Dashboard> result = new ArrayList<Dashboard>();

		Dashboard dash = new Dashboard();

		result.add(dash);

		return result;
	}

	@Override
	public void unbind(final Request<Dashboard> request, final Dashboard entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		Double totalAnnouncements = this.repository.totalNumberOfAnnouncements();
		Double totalCompanyRecords = this.repository.totalNumberOfCompanyRecords();
		Double totalInvestorRecords = this.repository.totalNumberOfInvestorRecords();
		Double maxRewardsRequests = this.repository.maxOfRewardsRequests();
		Double minRewardsRequests = this.repository.minOfRewardsRequests();
		Double avgRewardsRequests = this.repository.averageOfRewardsRequests();
		Double maxRewardsOffers = this.repository.maxOfRewardsOffers();
		Double minRewardsOffers = this.repository.minOfRewardsOffers();
		Double avgRewardsOffers = this.repository.averageOfRewardsOffers();
		Double stdRewardsRequests = this.repository.standardDeviationOfRewardsRequests();
		Double stdRewardsOffers = this.repository.standardDeviationOfRewardsOffers();
		Double[] numberOfCompaniesGroupedBySector = this.repository.numberOfCompaniesGroupedBySector();
		Double[] numberOfInvestorsGroupedBySector = this.repository.numberOfInvestorsGroupedBySector();

		ArrayList<LocalDate> lastFourWeeks = new ArrayList<LocalDate>();
		Calendar currCalendar = Calendar.getInstance();
		Date dateMinusMonth = currCalendar.getTime();

		for (int i = 0; i < 27; i++) {
			// Añade el día actual si no está ya presente
			if (!lastFourWeeks.contains(currCalendar.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())) {
				lastFourWeeks.add(currCalendar.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
			}
			// Restamos un día, lo convertimos a LocalDate para solo mostrar la fecha y lo añadimos al ArrayList
			currCalendar.add(Calendar.DAY_OF_YEAR, -1);
			LocalDate dateMinusDays = currCalendar.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			lastFourWeeks.add(dateMinusDays);
		}

		ArrayList<String> investorsSectors = new ArrayList<String>();
		ArrayList<String> companiesSectors = new ArrayList<String>();

		for (Investor a : this.repository.findEveryInvestor()) {
			if (!investorsSectors.contains(a.getSector())) {
				investorsSectors.add(a.getSector());
			}
		}

		for (CompanyRecord a : this.repository.findEveryCompanyRecord()) {
			if (!companiesSectors.contains(a.getSector())) {
				companiesSectors.add(a.getSector());
			}
		}

		entity.setCompaniesSectors(companiesSectors);
		entity.setInvestorsSectors(investorsSectors);
		entity.setLastFourWeeks(lastFourWeeks);

		Double avgNumberOfJobsPerEmployer = this.repository.averageNumberOfJobsPerEmployer();
		Double avgNumberOfApplicationsPerEmployer = this.repository.averageNumberOfApplicationsPerEmployer();
		Double avgNumberOfApplicationsPerWorker = this.repository.averageNumberOfApplicationsPerWorker();
		Double ratioOfDraftedJobs = this.repository.ratioOfDraftedJobs();
		Double ratioOfPublishedJobs = this.repository.ratioOfPublishedJobs();
		Double ratioOfPendingApplications = this.repository.ratioOfPendingApplications();
		Double ratioOfAcceptedApplications = this.repository.ratioOfAcceptedApplications();
		Double ratioOfRejectedApplications = this.repository.ratioOfRejectedApplications();
		//ArrayList<Double> companiesStatusPending = this.repository.getCompaniesStatusPending(dateMinusMonth);

		entity.setTotalAnnouncements(totalAnnouncements);
		entity.setTotalCompanyRecords(totalCompanyRecords);
		entity.setTotalInvestorRecords(totalInvestorRecords);
		entity.setMaxRewardsRequests(maxRewardsRequests);
		entity.setMinRewardsRequests(minRewardsRequests);
		entity.setAvgRewardsRequests(avgRewardsRequests);
		entity.setStdRewardsRequests(stdRewardsRequests);
		entity.setMinRewardsOffers(minRewardsOffers);
		entity.setMaxRewardsOffers(maxRewardsOffers);
		entity.setAvgRewardsOffers(avgRewardsOffers);
		entity.setStdRewardsOffers(stdRewardsOffers);
		entity.setNumberOfCompaniesGroupedBySector(numberOfCompaniesGroupedBySector);
		entity.setNumberOfInvestorsGroupedBySector(numberOfInvestorsGroupedBySector);

		entity.setAvgNumberOfJobsPerEmployer(avgNumberOfJobsPerEmployer);
		entity.setAvgNumberOfApplicationsPerEmployer(avgNumberOfApplicationsPerEmployer);
		entity.setAvgNumberOfApplicationsPerWorker(avgNumberOfApplicationsPerWorker);

		entity.setRatioOfPublishedJobs(ratioOfPublishedJobs);
		entity.setRatioOfDraftedJobs(ratioOfDraftedJobs);
		entity.setRatioOfAcceptedApplications(ratioOfAcceptedApplications);
		entity.setRatioOfPendingApplications(ratioOfPendingApplications);
		entity.setRatioOfRejectedApplications(ratioOfRejectedApplications);

		//entity.setCompaniesStatusPending(companiesStatusPending);

		request.unbind(entity, model, "totalAnnouncements", "totalCompanyRecords", "totalInvestorRecords", "maxRewardsRequests", "minRewardsRequests", "avgRewardsRequests", "stdRewardsRequests", "maxRewardsOffers", "minRewardsOffers", "avgRewardsOffers",
			"stdRewardsOffers", "avgNumberOfJobsPerEmployer", "avgNumberOfApplicationsPerEmployer", "avgNumberOfApplicationsPerWorker", "ratioOfDraftedJobs", "ratioOfPublishedJobs", "ratioOfPendingApplications", "ratioOfAcceptedApplications",
			"ratioOfRejectedApplications", "numberOfCompaniesGroupedBySector", "numberOfInvestorsGroupedBySector", "investorsSectors", "companiesSectors", "companiesStatusPending", "lastFourWeeks");
	}

}
