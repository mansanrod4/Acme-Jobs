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
	<acme:form-textbox readonly="true" code="administrator.auditor-rol-request.form.label.fullname" path="getUserFullName()"/>
	<acme:form-textbox readonly="true" code="administrator.auditor-rol-request.form.label.email" path="getUserEmail"/>
	<acme:form-checkbox code="administrator.auditor-rol-request.form.label.approved" path="approved"/>
	
	<acme:form-submit test="${command == 'update'}" code="administrator.auditor-rol-request.form.button.approve" action="/authenticated/auditor/create"/>
	<acme:form-return code="administrator.auditor-rol-request.form.button.return"/>
</acme:form>
