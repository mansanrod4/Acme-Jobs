<%@page language="java"%>

<%@taglib prefix="jstl" uri= "http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form readonly = "true">
	<acme:form-textbox code="employer.application.form.label.referenceNumber" path="referenceNumber"/>
	<acme:form-textarea code="employer.application.form.label.job" path="job"/>
	<acme:form-textarea code="employer.application.form.label.worker" path="worker"/>
	<acme:form-moment code="employer.application.form.label.creationMoment" path="creationMoment"/>
	<acme:form-textbox code="employer.application.form.label.status" path="status"/>
	<acme:form-textbox code="employer.application.form.label.statement" path="statement"/>
	<acme:form-textarea code="employer.application.form.label.skills" path="skills"/>
	<acme:form-textarea code="employer.application.form.label.qualifications" path="qualifications"/>
	
	<%-- Botones en la vista show --%>
	
	<acme:form-submit test="${command == 'show'}"		
		code="employer.job.form.button.accept"
		action="/employer/application/accept"/>
		
	<acme:form-submit test="${command == 'show'}"		
		code="employer.job.form.button.reject"
		action="/employer/application/reject"/>
		
	
	<acme:form-return code="employer.application.form.label.button.return"/>
</acme:form>