<%@page language="java"%>

<%@taglib prefix="jstl" uri= "http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form readonly = "false">

	<h2>
		<acme:message code="worker.application.form.label.title.JobInformation"/>
	</h2>
	
	<acme:form-textbox code="worker.application.form.label.jobTitle" path="jobTitle" readonly = "true"/>
	<acme:form-textbox code="worker.application.form.label.jobReference" path="jobReference" readonly = "true"/>
	<h2>
		<acme:message code="worker.application.form.label.title.ApplicationInformation"/>
	</h2>

	<jstl:if test="${command != 'create'}">
			<jstl:set var= "readOnly" value = "true"/>
	</jstl:if>
	<jstl:if test="${command != 'create' }">
		<acme:form-moment 
		code="worker.application.form.label.creationMoment" 
		path="creationMoment" 
		readonly="true"/>
	</jstl:if>
	
	<jstl:if test="${command != 'update'}">
		<acme:form-moment 
		code="worker.application.form.label.updateMoment" 
		path="updateMoment" 
		readonly="true"/>
	</jstl:if>
	
	<acme:form-textbox code="worker.application.form.label.referenceNumber" path="referenceNumber" readonly= "readOnly"/>
	<acme:form-textbox code="worker.application.form.label.status" path="status" readonly= "readOnly"/>
	<acme:form-textbox code="worker.application.form.label.statement" path="statement"/>
	<acme:form-textarea code="worker.application.form.label.skills" path="skills"/>
	<acme:form-textarea code="worker.application.form.label.qualifications" path="qualifications"/>
	
	<acme:form-submit test="${command == 'create'}"
 		code="worker.application.form.button.create"
		action="/worker/application/create?job_id=${id}"
		/> 
				 
	<acme:form-submit test="${command == 'show' || command == 'update'}"		
		code="worker.application.form.button.update"
		action="/worker/application/update"/>
		
	<acme:form-submit test="${command == 'show' || command == 'delete' || command == 'update'}"
		code="worker.application.form.button.delete"
		action="/worker/application/delete"/>
		
	<acme:form-return code="worker.application.form.label.button.return"/>
</acme:form>