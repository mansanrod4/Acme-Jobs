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
	<acme:form-textbox code="administrator.company-record.form.label.companyName" path="companyname" />
	<acme:form-textbox code="administrator.company-record.form.label.sector" path="sector" />
	<acme:form-textbox code="administrator.company-record.form.label.ceoName" path="ceoname" />
	<acme:form-textarea code="administrator.company-record.form.label.activDescription" path="activdescription"/>
	<acme:form-url code="administrator.company-record.form.label.website" path="website" />
	<acme:form-textbox code="administrator.company-record.form.label.phone" path="phone" />
	<acme:form-textbox code="administrator.company-record.form.label.email" path="email" />
	<acme:form-checkbox code="administrator.company-record.form.label.incorporated" path="incorporated" />
	<acme:form-integer code="administrator.company-record.form.label.rating" path="rating" placeholder=" "/>
  	
  	<acme:form-submit test="${command == 'show'}"
		code="administrator.company-record.form.button.update"
		action="/administrator/company-record/update"/>

	<acme:form-submit test="${command == 'show'}"
		code="administrator.company-record.form.button.delete"
		action="/administrator/company-record/delete"/>

	<acme:form-submit test="${command == 'create'}"
		code="administrator.company-record.form.button.create"
		action="/administrator/company-record/create"/>

	<acme:form-submit test="${command == 'update'}"
		code="administrator.company-record.form.button.update"
		action="/administrator/company-record/update"/>

	<acme:form-submit test="${command == 'delete'}"
		code="administrator.company-record.form.button.delete"
		action="/administrator/company-record/delete"/>

	<acme:form-return 
		code="administrator.company-record.form.button.return" />	
  	
</acme:form>
