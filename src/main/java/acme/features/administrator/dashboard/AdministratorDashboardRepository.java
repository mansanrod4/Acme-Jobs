
package acme.features.administrator.dashboard;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.companyRecords.CompanyRecord;
import acme.entities.investor.Investor;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AdministratorDashboardRepository extends AbstractRepository {

	@Query("Select count(a) from Announcement a")
	Double totalNumberOfAnnouncements();

	@Query("Select count(c) from CompanyRecord c")
	Double totalNumberOfCompanyRecords();

	@Query("Select count(i) from Investor i")
	Double totalNumberOfInvestorRecords();

	@Query("Select max(r.reward.amount) from Request r")
	Double maxOfRewardsRequests();

	@Query("Select min(r.reward.amount) from Request r")
	Double minOfRewardsRequests();

	@Query("Select avg(r.reward.amount) from Request r")
	Double averageOfRewardsRequests();

	@Query("Select stddev(r.reward.amount) from Request r")
	Double standardDeviationOfRewardsRequests();

	@Query("Select max(o.money.amount) from Offer o")
	Double maxOfRewardsOffers();

	@Query("Select min(o.money.amount) from Offer o")
	Double minOfRewardsOffers();

	@Query("Select avg(o.money.amount) from Offer o")
	Double averageOfRewardsOffers();

	@Query("Select stddev(o.money.amount) from Offer o")
	Double standardDeviationOfRewardsOffers();

	@Query("Select count(c) from CompanyRecord c group by c.sector")
	Double[] numberOfCompaniesGroupedBySector();

	@Query("Select a from CompanyRecord a")
	Collection<CompanyRecord> findEveryCompanyRecord();

	@Query("Select count(i) from Investor i group by i.sector")
	Double[] numberOfInvestorsGroupedBySector();

	@Query("Select a from Investor a")
	Collection<Investor> findEveryInvestor();

	@Query("Select avg(select count(j) from Job j where j.employer.id = e.id) from Employer e")
	Double averageNumberOfJobsPerEmployer();

	@Query("Select avg(select count(a) from Application a where exists(select j from Job j where j.employer.id = e.id and a.job.id = j.id)) from Employer e")
	Double averageNumberOfApplicationsPerEmployer();

	@Query("Select avg(select count (a) from Application a where a.worker.id = w.id) from Worker w")
	Double averageNumberOfApplicationsPerWorker();

	@Query("select 1.0 * count(j) / (select count (b) from Job b) from Job j where j.finalMode = false")
	Double ratioOfDraftedJobs();

	@Query("select 1.0 * count(j) / (select count (b) from Job b) from Job j where j.finalMode = true")
	Double ratioOfPublishedJobs();

	@Query("select 1.0 * count(a) / (select count (b) from Application b) from Application a where a.status = acme.entities.applications.ApplicationStatus.PENDING")
	Double ratioOfPendingApplications();

	@Query("select 1.0 * count(a) / (select count (b) from Application b) from Application a where a.status = acme.entities.applications.ApplicationStatus.ACCEPTED")
	Double ratioOfAcceptedApplications();

	@Query("select 1.0 * count(a) / (select count (b) from Application b) from Application a where a.status = acme.entities.applications.ApplicationStatus.REJECTED")
	Double ratioOfRejectedApplications();

}
