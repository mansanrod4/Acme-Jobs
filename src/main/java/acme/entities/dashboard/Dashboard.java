
package acme.entities.dashboard;

import java.io.Serializable;
import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Dashboard implements Serializable {

	//Identificador

	private static final long	serialVersionUID	= 1L;

	//Atributos

	Double						totalAnnouncements;
	Double						totalCompanyRecords;
	Double						totalInvestorRecords;
	Double						maxRewardsRequests;
	Double						minRewardsRequests;
	Double						avgRewardsRequests;
	Double						stdRewardsRequests;
	Double						maxRewardsOffers;
	Double						minRewardsOffers;
	Double						avgRewardsOffers;
	Double						stdRewardsOffers;
	Double						avgNumberOfJobsPerEmployer;
	Double						avgNumberOfApplicationsPerEmployer;
	Double						avgNumberOfApplicationsPerWorker;
	Double						ratioOfDraftedJobs;
	Double						ratioOfPublishedJobs;
	Double						ratioOfPendingApplications;
	Double						ratioOfAcceptedApplications;
	Double						ratioOfRejectedApplications;

	Double[]					numberOfCompaniesGroupedBySector;
	Double[]					numberOfInvestorsGroupedBySector;

	ArrayList<String>			investorsSectors;
	ArrayList<String>			companiesSectors;
}
