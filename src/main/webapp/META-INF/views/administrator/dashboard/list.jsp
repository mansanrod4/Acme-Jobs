<%@page language="java"%>

<%@taglib prefix="jstl" uri= "http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

	

<acme:form>
	<h2>
		<acme:message code="administrator.dashboard.form.label.title.statistics"/>
	</h2>
	
	<acme:form-textbox code="administrator.dashboard.form.label.totalAnnouncements" path="totalAnnouncements" />
	<acme:form-textbox code="administrator.dashboard.form.label.totalCompanyRecords" path="totalCompanyRecords" />
	<acme:form-textbox code="administrator.dashboard.form.label.totalInvestorRecords" path="totalInvestorRecords" />
	<acme:form-textbox code="administrator.dashboard.form.label.maxRewardsRequests" path="maxRewardsRequests" />
	<acme:form-textbox code="administrator.dashboard.form.label.minRewardsRequests" path="minRewardsRequests" />
	<acme:form-textbox code="administrator.dashboard.form.label.avgRewardsRequests" path="avgRewardsRequests"/>
	<acme:form-textbox code="administrator.dashboard.form.label.stdRewardsRequests" path="stdRewardsRequests"/>
	<acme:form-textbox code="administrator.dashboard.form.label.maxRewardsOffers" path="maxRewardsOffers" />
	<acme:form-textbox code="administrator.dashboard.form.label.minRewardsOffers" path="minRewardsOffers" />
	<acme:form-textbox code="administrator.dashboard.form.label.avgRewardsOffers" path="avgRewardsOffers"/>
	<acme:form-textbox code="administrator.dashboard.form.label.stdRewardsOffers" path="stdRewardsOffers"/>
	<acme:form-textbox code="administrator.dashboard.form.label.avgNumberOfJobsPerEmployer" path="avgNumberOfJobsPerEmployer"/>
	<acme:form-textbox code="administrator.dashboard.form.label.avgNumberOfApplicationsPerEmployer" path="avgNumberOfApplicationsPerEmployer"/>
	<acme:form-textbox code="administrator.dashboard.form.label.avgNumberOfApplicationsPerWorker" path="avgNumberOfApplicationsPerWorker"/>
	
	<h2>
		<acme:message code="administrator.dashboard.form.label.title.sectorCompaniesStatus"/>
	</h2>
	
	<div>
		<canvas id="canvas" width="600" height="400"></canvas>
	</div>
	
	<script type="text/javascript">
		$(document).ready(function(){
			 			
			var data = {
		
					labels: [<jstl:forEach var = "item" items="${companiesSectors}"> 
								"${item}",
							</jstl:forEach>
							],
					datasets : [{ 
						backgroundColor: "#ffa500",
						data :[<jstl:forEach var = "item" items="${numberOfCompaniesGroupedBySector}"> 
									<jstl:out value="${item}"/>,
								</jstl:forEach>
							]
					}]
			};
			
			var options = {
					responsive: true,
				    maintainAspectRatio: false,
					scales : {
							yAxes : [
								{
									ticks : {
										suggestedMin : 0.0,
										suggestedMax : 3.0,
										stepSize: 1
									}
								}
							]
						},
						legend : {
							display : false
						}
				};
				
				var canvas, context;
				
				canvas = document.getElementById("canvas");
				context = canvas.getContext("2d");
				new Chart(context, {
					type: "bar",
					data : data,
					options : options
				});
				
			});
	</script>
	
	<h2>
		<acme:message code="administrator.dashboard.form.label.title.sectorInvestorsStatus"/>
	</h2>
	
	<div>
		<canvas id="canvasInvestor" width="600" height="400"></canvas>
	</div>
	
	<script type="text/javascript">
		$(document).ready(function(){
			 			
			var data = {
		
					labels: [<jstl:forEach var = "item" items="${investorsSectors}"> 
								"${item}",
							</jstl:forEach>],
					datasets : [{ 
						backgroundColor: "#ffa500",
						data :[<jstl:forEach var = "item" items="${numberOfInvestorsGroupedBySector}"> 
							<jstl:out value="${item}"/>,
							</jstl:forEach>
							]
					}]
			};
			
			var options = {
					responsive: true,
				    maintainAspectRatio: false,
					scales : {
							yAxes : [
								{
									ticks : {
										suggestedMin : 0.0,
										suggestedMax : 3.0,
										stepSize: 1
									}
								}
							]
						},
						legend : {
							display : false
						}
				};
				
				var canvas, context;
				
				canvas = document.getElementById("canvasInvestor");
				context = canvas.getContext("2d");
				new Chart(context, {
					type: "bar",
					data : data,
					options : options
				});
				
			});
	</script>
	
	<h2>
		<acme:message code="administrator.dashboard.form.label.title.sectorJobStatus"/>
	</h2>
	
	<div>
		<canvas id="canvas2" width="200" height="400"></canvas>
	</div>
	
	<script type="text/javascript">
		$(document).ready(function(){ 
				
			var data = {
		
					labels: [
						"PUBLISHED", "DRAFTED" 
						],
					datasets : [{ 
						label: "Ratio",
						backgroundColor: ["#0fe600", "#dfe3e6"],
						data : [
							<jstl:out value="${ratioOfPublishedJobs}"/>,
							<jstl:out value="${ratioOfDraftedJobs}"/>
						]
					}]
			};
			
			var options = {
					responsive: true,
				    maintainAspectRatio: false,
					scales : {
							yAxes : [
								{
									ticks : {
										suggestedMin : 0.0,
										suggestedMax : 1.0
									}
								}
							]
						},
						legend : {
							display : false
						}
				};
				
				var canvas, context;
				
				canvas = document.getElementById("canvas2");
				context = canvas.getContext("2d");
				new Chart(context, {
					type: "bar",
					data : data,
					options : options
				});
				
			});
	</script>
	
	<h2>
		<acme:message code="administrator.dashboard.form.label.title.sectorApplicationStatus"/>
	</h2>
	
	<div>
		<canvas id="canvas3" width="200" height="400"></canvas>
	</div>
	
	<script type="text/javascript">
		$(document).ready(function(){ 
				
			var data = {
		
					labels: [
						"ACCEPTED", "PENDING", "REJECTED"
						],
					datasets : [{ 
						label: "Ratio",
						backgroundColor: ["#0fe600", "#dfe3e6",  "#e62e00"],
						data : [
							<jstl:out value="${ratioOfAcceptedApplications}"/>,
							<jstl:out value="${ratioOfPendingApplications}"/>,
							<jstl:out value="${ratioOfRejectedApplications}"/>
						]
					}]
			};
			
			var options = {
					responsive: true,
				    maintainAspectRatio: false,
					scales : {
							yAxes : [
								{
									ticks : {
										suggestedMin : 0.0,
										suggestedMax : 1.0
									}
								}
							]
						},
						legend : {
							display : false
						}
				};
				
				var canvas, context;
				
				canvas = document.getElementById("canvas3");
				context = canvas.getContext("2d");
				new Chart(context, {
					type: "bar",
					data : data,
					options : options
				});
				
			});
	</script>
	
	<div>
		<h2>
			<acme:message code="administrator.dashboard.form.label.title.Applications"/>
		</h2>
		<p>
			<acme:message code="administrator.dashboard.form.label.title.Applications.subtext"/>
		</p>
	</div>
	
	<div>
		<canvas id="ApplicationStatusChart" width="600" height="400"></canvas>
	</div>
	
	<script type="text/javascript">
		$(document).ready(function(){
			
		var ApplicationStatusChart = document.getElementById("ApplicationStatusChart");

		var pendingData = {
 			label: "Pending",
  			data: [<jstl:forEach var = "item" items="${companiesStatusPending}"> 
						<jstl:out value="${item}"/>,
					</jstl:forEach>],
  		    backgroundColor: "#dfe3e6",
  		   	borderWidth: 1
		};

		var acceptedData = {
  			label: "Accepted",
  			data: [<jstl:forEach var = "item" items="${companiesStatusAccepted}"> 
						<jstl:out value="${item}"/>,
				   </jstl:forEach>],
  			backgroundColor: "#0fe600",
  			borderWidth: 1
		};
		
		var rejectedData = {
	 			label: "Rejected",
	  			data: [<jstl:forEach var = "item" items="${companiesStatusRejected}"> 
							<jstl:out value="${item}"/>,
						</jstl:forEach>],
	  		    backgroundColor: "#e62e00",
	  		   	borderWidth: 1
			};

		var Data = {
  			labels: [<jstl:forEach var = "item" items="${lastFourWeeks}"> 
						"${item}",
					</jstl:forEach>],
  			datasets: [pendingData, acceptedData, rejectedData]
		};

		var chartOptions = {
				responsive: true,
			    maintainAspectRatio: false,
  			scales: {
    			xAxes: [{
      				barPercentage: 1,
      				categoryPercentage: 1
    			}],
   			 	yAxes: [{
   			 				ticks : {
								suggestedMin : 0.0,
								suggestedMax : 4.0,
								stepSize: 1,
								sampleSize : 3
								
							}
						}
   			 			]
  					}
			};

		var barChart = new Chart(ApplicationStatusChart, {
  			type: 'bar',
  			data: Data,
  			options: chartOptions
		  });
		});
	</script>
	
	<acme:form-return code="administrator.dashboard.form.button.return" />	
	
</acme:form>