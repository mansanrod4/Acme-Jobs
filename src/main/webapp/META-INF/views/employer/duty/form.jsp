<%@page language="java"%>

<%@taglib prefix="jstl" uri= "http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form>
	<acme:form-textbox code="employer.duty.form.label.title" path="title"/>
	<acme:form-textbox code="employer.duty.form.label.description" path="description"/>
	<acme:form-moment code="employer.duty.form.label.percentageTimeWeek" path="percentageTimeWeek"/>
	
	<h2>
		<acme:message code="employer.duty.form.label.title.JobInformation"/>
	</h2>
	
	<acme:form-textbox code="employer.duty.form.label.jobTitle" path="jobTitle"/>
	<acme:form-textbox code="employer.duty.form.label.jobReference" path="jobReference"/>
	<acme:form-textbox code="employer.duty.form.label.jobEmployer" path="jobEmployer"/>
	
	<acme:form-return code="employer.job.form.button.return"/>
</acme:form>