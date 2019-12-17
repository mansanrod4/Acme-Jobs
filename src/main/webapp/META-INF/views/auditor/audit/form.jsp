<%@page language="java"%>

<%@taglib prefix="jstl" uri= "http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form>
	<acme:form-textbox code="auditor.audit.form.label.reference" path="title"/>
	
	<jstl:if test="${command != 'create'}">
		<acme:form-moment 
		code="auditor.audit.form.label.moment" 
		path="moment"
		readonly="true"/>
	</jstl:if>
	
	<acme:form-textarea code="auditor.audit.form.label.body" path="body"/>
	<acme:form-textbox code="auditor.audit.form.label.status" path="status" placeholder="DRAFT/PUBLISHED"/>
	
	<acme:form-submit test = "${command == 'create'}"
		code = "auditor.audit.form.button.create" 
		action = "/auditor/audit/create?job_id=${job_id}"/>

	<acme:form-return code="auditor.audit.form.button.return"/>
</acme:form>