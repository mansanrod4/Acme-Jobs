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
	
	<%-- Botones en la vista show --%>	
	
	<acme:form-submit test="${command == 'show' && !finalMode}"		
		code="employer.job.form.button.update"
		action="/employer/job/update"/>
		
	<acme:form-submit test="${command == 'show' && !finalMode}"		
		code="employer.job.form.button.publish"
		action="/employer/job/publish"/>
		
	<acme:form-submit test="${command == 'show'}"			
		code="employer.job.form.button.delete"
		action="/employer/job/delete"/>

	<%-- Botones en la vista update --%>	

	<acme:form-submit test="${command == 'update'}"
		code="employer.job.form.button.update"
		action="/employer/job/update"/>
		
	<acme:form-submit test="${command == 'update'}"
		code="employer.job.form.button.publish"
		action="/employer/job/publish"
	/>

	<acme:form-submit test="${command == 'update'}"			
		code="employer.job.form.button.delete"
		action="/employer/job/delete"/>

	<%-- Botones en la vista publish --%>
				
	<acme:form-submit test="${command == 'publish'}"
		code="employer.job.form.button.update"
		action="/employer/job/update"/>	
		
	<acme:form-submit test="${command == 'publish'}"
		code="employer.job.form.button.publish"
		action="/employer/job/publish"/>
		
	<acme:form-submit test="${command == 'publish'}"		
		code="employer.job.form.button.delete"
		action="/employer/job/delete"/>
		
		
		
							
	<acme:form-submit test="${command == 'delete'}"					
		code="employer.job.form.button.delete"
		action="/employer/job/delete"/>
							
	<%-- Botones en la vista create --%>			
				
	<acme:form-submit test="${command == 'create'}"
		code="employer.job.form.button.create"
		action="/employer/job/create"/>
		
	<acme:form-submit test="${command == 'create'}"
		code="employer.job.form.button.create&publish"
		action="/employer/job/create_and_publish"/>
		
	<%-- Botones en la vista create & publish --%>
		
	<acme:form-submit test="${command == 'create_and_publish'}"
		code="employer.job.form.button.create"
		action="/employer/job/create"/>
		
	<acme:form-submit test="${command == 'create_and_publish'}"
		code="employer.job.form.button.create&publish"
		action="/employer/job/create_and_publish"/>
		
	<%-- Mostrar duties siempre que no se esté en la ventana de crear el job --%>
	
	<acme:form-submit test="${command != 'create' && command != 'create_and_publish'}" 
		code="employer.job.form.button.duties" 
		action="/employer/duty/list?job_id=${id}" 
		method="get"/>
	
	<acme:form-return code="employer.job.form.button.return"/>
	
</acme:form>