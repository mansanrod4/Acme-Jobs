<%@page language="java"%>

<%@taglib prefix="jstl" uri= "http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form>
	<acme:form-textbox code="administrator.investor.form.label.name" path="name"/>
	<acme:form-textbox code="administrator.investor.form.label.sector" path="sector"/>
	<acme:form-textarea code="administrator.investor.form.label.investingStatement" path="investingStatement"/>
	<acme:form-integer code="administrator.investor.form.label.star" path="star"/>
	
	<acme:form-submit test="${command == 'show'}"
		code="administrator.investor.form.button.update"
		action="/administrator/investor/update"/>
		
	<acme:form-submit test="${command == 'show'}"
		code="administrator.investor.form.button.delete"
		action="/administrator/investor/delete"/>
		
	<acme:form-submit test="${command == 'create'}"
		code="administrator.investor.form.button.create"
		action="/administrator/investor/create"/>
		
	<acme:form-submit test="${command == 'update'}"
		code="administrator.investor.form.button.update"
		action="/administrator/investor/update"/>
		
	<acme:form-submit test="${command == 'delete'}"
		code="administrator.investor.form.button.delete"
		action="/administrator/investor/delete"/>
	
	<acme:form-return 
		code="administrator.investor.form.button.return" />	
</acme:form>