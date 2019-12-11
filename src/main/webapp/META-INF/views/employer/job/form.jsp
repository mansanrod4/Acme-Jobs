<%@page language="java"%>

<%@taglib prefix="jstl" uri= "http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form readonly = "false">

		<jstl:if test="${finalMode && command != 'update' && command != 'publish'}">
			<jstl:set var= "readOnly" value = "true"/>
		</jstl:if>
		
		<acme:form-textbox code="employer.job.form.label.reference" path="reference" readonly= "${readOnly}" />
		<acme:form-textbox code="employer.job.form.label.title" path="title" readonly= "${readOnly}"/>
		<acme:form-moment code="employer.job.form.label.deadLine" path="deadLine" readonly= "${readOnly}"/>
		<acme:form-money code="employer.job.form.label.salary" path="salary" readonly= "${readOnly}"/>
		<acme:form-url code="employer.job.form.label.moreInfo" path="moreInfo" readonly= "${readOnly}"/>
		<acme:form-textarea code="employer.job.form.label.description" path="description" readonly= "${readOnly}"/>
	
	
	<%-- Boton Publish --%>	
		
	<acme:form-submit test="${(command == 'show' && !finalMode) || command == 'update' || command == 'publish' }"		
		code="employer.job.form.button.publish"
		action="/employer/job/publish"/>
		
	<%-- Boton Update --%>	
	
	<acme:form-submit test="${(command == 'show' && !finalMode) || command == 'update' || command == 'publish' }"		
		code="employer.job.form.button.update"
		action="/employer/job/update"/>
		
		
	<%-- Boton Delete --%>	
	
	<acme:form-submit test="${(command == 'delete' && !canDelete) || (command == 'publish' && !canDelete) || (command == 'show' && !canDelete) || command == 'update' && !canDelete}"					
		code="employer.job.form.button.delete"
		action="/employer/job/delete"/>
		
		
	<%-- Boton Create --%>				
				
	<acme:form-submit test="${command == 'create'}"
		code="employer.job.form.button.create"
		action="/employer/job/create"/>

		
	<%-- Mostrar duties siempre que no se esté en la ventana de crear el job --%>
	
	<acme:form-submit test="${command != 'create'}" 
		code="employer.job.form.button.duties" 
		action="/employer/duty/list?job_id=${id}" 
		method="get"/>
		
	<acme:form-submit test="${command != 'create' && !finalMode}" 
		code="employer.job.form.button.duties.create" 
		action="/employer/duty/create?job_id=${id}" 
		method="get"/>
	
	<acme:form-return code="employer.job.form.button.return"/>
	
</acme:form>