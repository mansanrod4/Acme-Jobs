<%@page language="java"%>

<%@taglib prefix="jstl" uri= "http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form readonly = "false">

	<jstl:if test="${command != 'create'}">
		<jstl:set var= "readOnly" value = "true"/>
	</jstl:if>

	<h2>
		<acme:message code="worker.application.form.label.title.JobInformation"/>
	</h2>
	
	<acme:form-textbox code="worker.application.form.label.jobTitle" path="jobTitle" readonly = "true"/>
	<acme:form-textbox code="worker.application.form.label.jobReference" path="jobReference" readonly = "true"/>
	<acme:form-moment code="worker.application.form.label.jobDeadline" path="jobDeadline" readonly = "true"/>
	
	<h2>
		<acme:message code="worker.application.form.label.title.ApplicationInformation"/>
	</h2>

	<jstl:if test="${command != 'create' }">
		<acme:form-moment 
		code="worker.application.form.label.creationMoment" 
		path="creationMoment" 
		readonly="true"/>
	</jstl:if>
	
	<acme:form-textbox code="worker.application.form.label.referenceNumber" path="referenceNumber" placeholder="'EEEE-JJJJ:WWWW'" readonly ="${readOnly}"/>
	<acme:form-textarea code="worker.application.form.label.statement" path="statement" readonly ="${readOnly}"/>
	<acme:form-textarea code="worker.application.form.label.skills" path="skills" readonly ="${readOnly}"/>
	<acme:form-textarea code="worker.application.form.label.qualifications" path="qualifications" readonly ="${readOnly}"/>
	
	<jstl:if test="${command != 'create'}">
		<acme:form-textbox code="worker.application.form.label.status" path="status" readonly= "true"/>
	</jstl:if>
	
	<jstl:if test="${isAccepterOrRejected && command != 'create'}">
		<acme:form-textarea code="worker.application.form.label.justification" path="justification" readonly= "true"/>
	</jstl:if>
		
	<acme:form-submit test="${command == 'create'}"
 		code="worker.application.form.button.create"
		action="/worker/application/create?job_id=${job_id}"
		/> 
		
	<acme:form-return code="worker.application.form.label.button.return"/>
</acme:form>