<%--
- form.jsp
-
- Copyright (c) 2019 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page import="acme.features.authenticated.auditorRolRequest.AuthenticatedAuditorRolRequestRepository"%>
<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form>

	<jstl:if test="${command == 'create' && hasRequest && !isApproved}">
		<acme:print value="Tu solicitud esta pendiente de Aprovacion"/>
	</jstl:if>
	
	<jstl:if test="${command == 'create' && !hasRequest}">
		<acme:print value="Antes de ser Auditor necesitas la aprobacion de un administrador"/>
	</jstl:if>
	
	<jstl:if test="${command == 'create' && hasRequest && isApproved}">
		<acme:form-textbox code="authenticated.auditor.form.label.firm" path="firm"/>
		<acme:form-textbox code="authenticated.auditor.form.label.statement" path="statement"/>
	</jstl:if>
	
	<acme:form-submit test="${command == 'create' && !hasRequest}" code="authenticated.auditor.form.button.create-request" action="/authenticated/auditor-rol-request/create"/>
	<acme:form-submit test="${command == 'create' && hasRequest && isApproved}" code="authenticated.auditor.form.button.create" action="/authenticated/auditor/create"/>
	<acme:form-submit test="${command == 'update'}" code="authenticated.auditor.form.button.update" action="/authenticated/auditor/update"/>
	<acme:form-return code="authenticated.auditor.form.button.return"/>
</acme:form>
