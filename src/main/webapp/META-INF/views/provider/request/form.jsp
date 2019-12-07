<%@page language="java"%>

<%@taglib prefix="jstl" uri= "http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form>
	<acme:form-textbox code="provider.request.form.label.title" path="title"/>
	
	<jstl:if test="${command != 'create'}">		
		<acme:form-moment 
		code="provider.request.form.label.creationMoment" 
		path="creationMoment"
		readonly= "true"/>
	</jstl:if>
	
	<acme:form-moment code="provider.request.form.label.deadLine" path="deadLine"/>
	<acme:form-textarea code="provider.request.form.label.text" path="text"/>
	<acme:form-money code="provider.request.form.label.reward" path="reward"/>
	<acme:form-textbox code="provider.request.form.label.ticker" path="ticker"/>
	<acme:form-checkbox code="provider.request.form.label.accept" path="accept"/>
	
	<acme:form-submit test="${command == 'create'}"
		code="provider.request.form.button.create"
		action="/provider/request/create"/>
	
	<acme:form-return 
	code="provider.request.form.button.return" />	
	
</acme:form>