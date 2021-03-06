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

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form>
	<acme:print value="${user}"/>
	<acme:message code="administrator.auditor-rol-request.text"/><br/><br/>
	<acme:form-submit test="${command == 'show'}" code="administrator.auditor-rol-request.form.button.approve" action="/administrator/auditor-rol-request/accept"/>
	<acme:form-return code="administrator.auditor-rol-request.form.button.return"/>
</acme:form>
