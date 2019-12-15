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
	<acme:form-textbox code="authenticated.auditor.form.label.firm" path="firm"/>
	<acme:form-textbox code="authenticated.auditor.form.label.statement" path="statement"/>
	
	<acme:form-submit test="${command == 'create'}" code="authenticated.auditor.form.button.create" action="/authenticated/auditor/create"/>
	<acme:form-submit test="${command == 'update'}" code="authenticated.auditor.form.button.update" action="/authenticated/auditor/update"/>
	<acme:form-return code="authenticated.auditor.form.button.return"/>
</acme:form>
