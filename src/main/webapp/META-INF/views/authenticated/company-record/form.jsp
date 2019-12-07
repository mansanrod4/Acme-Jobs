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

<acme:form readonly="true">
	<acme:form-textbox code="authenticated.company-record.form.label.companyName" path="companyname" />
	<acme:form-textbox code="authenticated.company-record.form.label.sector" path="sector" />
	<acme:form-textbox code="authenticated.company-record.form.label.ceoName" path="ceoname" />
	<acme:form-textarea code="authenticated.company-record.form.label.activDescription" path="activdescription"/>
	<acme:form-url code="authenticated.company-record.form.label.website" path="website" />
	<acme:form-textbox code="authenticated.company-record.form.label.phone" path="phone" />
	<acme:form-textbox code="authenticated.company-record.form.label.email" path="email" />
	<acme:form-checkbox code="authenticated.company-record.form.label.incorporated" path="incorporated" readonly="True"/>
	<acme:form-integer code="authenticated.company-record.form.label.rating" path="rating" placeholder=" "/>
	
  	<acme:form-return code="authenticated.company-record.button.return"/>
</acme:form>
