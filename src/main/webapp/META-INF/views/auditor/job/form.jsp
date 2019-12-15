<%@page language="java"%>

<%@taglib prefix="jstl" uri= "http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form readonly = "false">

	<acme:form-textbox code="auditor.job.form.label.reference" path="reference"/>
	<acme:form-textbox code="auditor.job.form.label.title" path="title"/>
	<acme:form-moment code="auditor.job.form.label.deadLine" path="deadLine"/>
	<acme:form-money code="auditor.job.form.label.salary" path="salary"/>
	<acme:form-url code="auditor.job.form.label.moreInfo" path="moreInfo"/>
	<acme:form-textarea code="auditor.job.form.label.description" path="description"/>
	
	<acme:form-submit test="${command == 'show'}" 
		code="auditor.job.form.button.audits" 
		action="/auditor/audit/list?job_id=${id}" 
		method="get"
	/>
		
	<acme:form-submit test="${command == 'show'}"
		code="auditor.audit.form.button.create"
		action="/auditor/audit/create?job_id=${id}"
		method = "get"/>
		
	<acme:form-submit test="${command == 'create'}"
		code="auditor.audit.form.button.create"
		action="/auditor/audit/create?job_id=${id}"
		method = "get"/>
	
	<acme:form-return code="auditor.job.form.button.return"/>		
</acme:form>