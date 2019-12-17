<%@page language="java"%>

<%@taglib prefix="jstl" uri= "http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form>

	<jstl:if test="${command != 'create' }">
		<acme:form-moment 
		code="worker.application.form.label.creationMoment" 
		path="creationMoment" 
		readonly="true"/>
	</jstl:if>
	
	<acme:form-hidden path="id" />
	<acme:form-hidden path="jobId"/>
	<acme:form-textbox code="worker.application.form.label.referenceNumber" path="referenceNumber"/>
	<acme:form-textbox code="worker.application.form.label.status" path="status"/>
	<acme:form-textbox code="worker.application.form.label.statement" path="statement"/>
	<acme:form-textarea code="worker.application.form.label.skills" path="skills"/>
	<acme:form-textarea code="worker.application.form.label.qualifications" path="qualifications"/>
	
	<acme:form-submit test="${command =='create' || command =='update'}"
		code="worker.application.form.button.create"
		action="/worker/application/create"/>
		
	<acme:form-submit test="${command == 'show' || command == 'delete' || command == 'update'}"
		code="worker.application.form.button.delete"
		action="/worker/application/delete"/>
	
	
	<acme:form-return code="worker.application.form.label.button.return"/>
</acme:form>