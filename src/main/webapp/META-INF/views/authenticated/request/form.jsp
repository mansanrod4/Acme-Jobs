<%@page language="java"%>

<%@taglib prefix="jstl" uri= "http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form  readonly="true">
	<acme:form-textbox code="authenticated.request.form.label.title" path="title"/>
	<acme:form-textbox code="authenticated.request.form.label.ticker" path="ticker"/>
	<acme:form-moment code="authenticated.request.form.label.creationMoment" path="creationMoment"/>
	<acme:form-moment code="authenticated.request.form.label.deadLine" path="deadLine"/>
	<acme:form-textarea code="authenticated.request.form.label.text" path="text"/>
	<acme:form-textbox code="authenticated.request.form.label.reward" path="reward"/>
	
	<acme:form-return code="authenticated.request.form.button.return" />	
</acme:form>