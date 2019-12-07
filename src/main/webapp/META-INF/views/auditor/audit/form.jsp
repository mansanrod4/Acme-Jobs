<%@page language="java"%>

<%@taglib prefix="jstl" uri= "http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form>
	<acme:form-textbox code="auditor.audit.form.label.reference" path="title"/>
	<acme:form-moment code="auditor.audit.form.label.moment" path="moment"/>
	<acme:form-textarea code="auditor.audit.form.label.body" path="body"/>
	<acme:form-textbox code="auditor.audit.form.label.status" path="status"/>
	
	<acme:form-return code="auditor.audit.form.button.return"/>
</acme:form>