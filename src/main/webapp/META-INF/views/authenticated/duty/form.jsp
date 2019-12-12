<%@page language="java"%>

<%@taglib prefix="jstl" uri= "http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form>

	<h2>
		<acme:message code="authenticated.duty.form.label.title.JobInformation"/>
	</h2>
	
	<acme:form-textbox code="authenticated.duty.form.label.jobTitle" path="jobTitle"/>
	<acme:form-textbox code="authenticated.duty.form.label.jobReference" path="jobReference"/>
	<acme:form-textbox code="authenticated.duty.form.label.jobEmployer" path="jobEmployer"/>
	
	<h2>
		<acme:message code="authenticated.duty.form.label.title.DutyInformation"/>
	</h2>
		
	
	<acme:form-textbox code="authenticated.duty.form.label.title" path="title"/>
	<acme:form-textarea code="authenticated.duty.form.label.description" path="description"/>
	<acme:form-double code="authenticated.duty.form.label.percentageTimeWeek" path="percentageTimeWeek"/>
	
	

	<acme:form-return code="authenticated.duty.form.button.return"/>
</acme:form>