<%@page language="java"%>

<%@taglib prefix="jstl" uri= "http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form readonly="true">
	<acme:form-textbox code="worker.job.form.label.reference" path="reference"/>
	<acme:form-textbox code="worker.job.form.label.title" path="title"/>
	<acme:form-moment code="worker.job.form.label.deadLine" path="deadLine"/>
	<acme:form-money code="worker.job.form.label.salary" path="salary"/>
	<acme:form-url code="worker.job.form.label.moreInfo" path="moreInfo"/>
	<acme:form-textarea code="worker.job.form.label.description" path="description"/>
	
	<acme:form-submit test="${command == 'show'}"
		code="worker.job.form.button.duties"
		action="/authenticated/duty/list?job_id=${id}" 
		method="get"/>

	<acme:form-submit test="${command == 'show'}"
		code="worker.job.form.button.applications"
		action="/worker/application/create?job_id=${id}"
		method="get"/>
		
	<acme:form-return code="worker.job.form.button.return"/>
</acme:form>