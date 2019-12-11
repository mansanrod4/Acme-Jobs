<%--
- menu.jsp
-
- Copyright (c) 2019 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page language="java" import="acme.framework.helpers.PrincipalHelper,acme.entities.roles.Provider,acme.entities.roles.Consumer"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:menu-bar code="master.menu.home">
	<acme:menu-left>
		<acme:menu-option code="master.menu.anonymous" access="isAnonymous()">
			<acme:menu-suboption code="master.menu.anonymous.announcements" action="/anonymous/announcement/list"/>
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.anonymous.investorrecord" action="/anonymous/investor/list"/>
			<acme:menu-suboption code="master.menu.anonymous.companyrecords" action="/anonymous/company-record/list"/>
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.anonymous.topinvestorrecords" action="/anonymous/top-investor/list"/>
			<acme:menu-suboption code="master.menu.anonymous.topcompanyrecords" action="/anonymous/top-company-record/list"/>
		</acme:menu-option>
		
		<acme:menu-option code="master.menu.authenticated" access="hasRole('Authenticated')">
			<acme:menu-suboption code="master.menu.authenticated.announcement" action="/authenticated/announcement/list"/>			
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.authenticated.challenge" action="/authenticated/challenge/list"/>
        	<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.authenticated.companyrecords" action="/authenticated/company-record/list"/>
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.authenticated.investorrecords" action="/authenticated/investor/list"/>
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.authenticated.offer" action="/authenticated/offer/list"/>     
      		<acme:menu-suboption code="master.menu.authenticated.request" action="/authenticated/request/list"/>   
      		<acme:menu-separator/>
      		<acme:menu-suboption code="master.menu.authenticated.jobs.list" action="/authenticated/job/list"/>
      		<acme:menu-separator/>
      		<acme:menu-suboption code="master.menu.authenticated.messagethread.mine" action="/authenticated/messagethread/list-mine"/>  		      		
		</acme:menu-option>
		
		<acme:menu-option code="master.menu.administrator" access="hasRole('Administrator')">
			<acme:menu-suboption code="master.menu.administrator.announcements" action="/administrator/announcement/list"/>	
			<acme:menu-suboption code="master.menu.administrator.announcements.create" action="/administrator/announcement/create"/>
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.administrator.challenges" action="/administrator/challenge/list"/>	
			<acme:menu-suboption code="master.menu.administrator.challenges.create" action="/administrator/challenge/create"/>	
      		<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.administrator.investor" action="/administrator/investor/list"/>	
			<acme:menu-suboption code="master.menu.administrator.investor.create" action="/administrator/investor/create"/>
	   		<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.administrator.companyrecords.list" action="/administrator/company-record/list"/>
			<acme:menu-suboption code="master.menu.administrator.companyrecords.create" action="/administrator/company-record/create"/>
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.administrator.banners.commercial-banner" action="/administrator/commercial-banner/list"/>	
			<acme:menu-suboption code="master.menu.administrator.banners.commercial-banner.create" action="/administrator/commercial-banner/create"/>
			<acme:menu-suboption code="master.menu.administrator.banners.non-commercial-banner" action="/administrator/non-commercial-banner/list"/>	
			<acme:menu-suboption code="master.menu.administrator.banners.non-commercial-banner.create" action="/administrator/non-commercial-banner/create"/>
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.administrator.user-accounts" action="/administrator/user-account/list"/>
			<acme:menu-suboption code="master.menu.administrator.sysconfig" action="/administrator/sysconfig/list"/>
			<acme:menu-suboption code="master.menu.administrator.dashboard" action="/administrator/dashboard/list"/>
			<acme:menu-suboption code="master.menu.administrator.shutdown" action="/master/shutdown"/>
			
		</acme:menu-option>
		
 		
		<acme:menu-option code="master.menu.worker" access="hasRole('Worker')">
				
			<acme:menu-suboption code="master.menu.worker.applications.list" action="/worker/application/list_mine"/>
				
		</acme:menu-option>
		

		<acme:menu-option code="master.menu.provider" access="hasRole('Provider')">
			<acme:menu-suboption code="master.menu.provider.requests.create" action="/provider/request/create"/>
		</acme:menu-option>

		<acme:menu-option code="master.menu.consumer" access="hasRole('Consumer')">
			<acme:menu-suboption code="master.menu.consumer.offers.create" action="/consumer/offer/create"/>
		</acme:menu-option>
		
		<acme:menu-option code="master.menu.employer" access="hasRole('Employer')">
			<acme:menu-suboption code="master.menu.employer.jobs.list" action="/employer/job/list_mine"/>
			<acme:menu-suboption code="master.menu.employer.jobs.create" action="/employer/job/create"/>
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.employer.applications.list" action="/employer/application/list_mine"/>
		</acme:menu-option>
		

		<acme:menu-option code="master.menu.sponsor" access="hasRole('Sponsor')">
			<acme:menu-suboption code="master.menu.sponsor.commercialbanner.list" action="/sponsor/commercial-banner/list"/>
			<acme:menu-suboption code="master.menu.sponsor.noncommercialbanner.list" action="/sponsor/non-commercial-banner/list"/>
		</acme:menu-option>

		<acme:menu-option code="master.menu.auditor" access="hasRole('Auditor')">
			<acme:menu-suboption code="master.menu.auditor.jobs.list" action="/auditor/job/list_recorded"/>
			<acme:menu-suboption code="master.menu.auditor.notrecorded.list" action="/auditor/job/list_nonrecorded"/>
		</acme:menu-option>
		
		
	</acme:menu-left>

	<acme:menu-right>
		<acme:menu-option code="master.menu.sign-up" action="/anonymous/user-account/create" access="isAnonymous()"/>
		<acme:menu-option code="master.menu.sign-in" action="/master/sign-in" access="isAnonymous()"/>

		<acme:menu-option code="master.menu.user-account" access="isAuthenticated()">
			<acme:menu-suboption code="master.menu.user-account.general-data" action="/authenticated/user-account/update"/>
			<acme:menu-suboption code="master.menu.user-account.become-provider" action="/authenticated/provider/create" access="!hasRole('Provider')"/>
			<acme:menu-suboption code="master.menu.user-account.provider" action="/authenticated/provider/update" access="hasRole('Provider')"/>
			<acme:menu-suboption code="master.menu.user-account.become-consumer" action="/authenticated/consumer/create" access="!hasRole('Consumer')"/>
			<acme:menu-suboption code="master.menu.user-account.consumer" action="/authenticated/consumer/update" access="hasRole('Consumer')"/>
			<acme:menu-suboption code="master.menu.user-account.become-employer" action="/authenticated/employer/create" access="!hasRole('Employer')"/>
			<acme:menu-suboption code="master.menu.user-account.employer" action="/authenticated/employer/update" access="hasRole('Employer')"/>
			<acme:menu-suboption code="master.menu.user-account.become-worker" action="/authenticated/worker/create" access="!hasRole('Worker')"/>
			<acme:menu-suboption code="master.menu.user-account.worker" action="/authenticated/worker/update" access="hasRole('Worker')"/>
		</acme:menu-option>

		<acme:menu-option code="master.menu.sign-out" action="/master/sign-out" access="isAuthenticated()"/>
	</acme:menu-right>
</acme:menu-bar>
