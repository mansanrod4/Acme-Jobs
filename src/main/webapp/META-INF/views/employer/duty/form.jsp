<%@page language="java"%>

<%@taglib prefix="jstl" uri= "http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form>

	<h2>
		<acme:message code="employer.duty.form.label.title.JobInformation"/>
	</h2>
	
	<acme:form-textbox code="employer.duty.form.label.jobTitle" path="jobTitle" readonly = "true"/>
	<acme:form-textbox code="employer.duty.form.label.jobReference" path="jobReference" readonly = "true"/>
	<acme:form-textbox code="employer.duty.form.label.jobEmployer" path="jobEmployer" readonly = "true"/>	
			
	<h2>
		<acme:message code="employer.duty.form.label.title.DutyInformation"/>
	</h2>
		
	<acme:form-textbox code="employer.duty.form.label.title" path="title" readonly = "${isNotFinalMode}"/>
	<acme:form-textarea code="employer.duty.form.label.description" path="description" readonly = "${isNotFinalMode}"/>
	<acme:form-double code="employer.duty.form.label.percentageTimeWeek" path="percentageTimeWeek" readonly = "${isNotFinalMode}"/>
	
		
	<acme:form-submit test="${(command == 'show' || command == 'update' || command == 'delete') && !isNotFinalMode}"
		code="employer.duty.form.button.update"
		action="/employer/duty/update"/>
	
	<acme:form-submit test="${(command == 'show' || command == 'update' || command == 'delete') && !isNotFinalMode}"
		code="employer.duty.form.button.delete"
		action="/employer/duty/delete"/>
	
	<acme:form-submit test="${command == 'create'}"
		code="employer.duty.form.button.create"
		action="/employer/duty/create?job_id=${job_id}"/>
	
	<acme:form-return code="employer.job.form.button.return"/>
</acme:form>