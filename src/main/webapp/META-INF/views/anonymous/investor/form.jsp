<%@page language="java"%>

<%@taglib prefix="jstl" uri= "http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form readonly="true">
	<acme:form-textbox code="anonymous.investor.form.label.name" path="name"/>
	<acme:form-textbox code="anonymous.investor.form.label.sector" path="sector"/>
	<acme:form-textarea code="anonymous.investor.form.label.investingStatement" path="investingStatement"/>
	<acme:form-integer code="anonymous.investor.form.label.star" path="star"/>
	
	<acme:form-return code="anonymous.investor.form.button.return" />	
</acme:form>