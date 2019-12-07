<%@page language="java"%>

<%@taglib prefix="jstl" uri= "http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form>
	<acme:form-textbox code="worker.application.form.label.referenceNumber" path="referenceNumber"/>
	<acme:form-textarea code="worker.application.form.label.job" path="job"/>
	<acme:form-textarea code="worker.application.form.label.worker" path="worker"/>
	<acme:form-moment code="worker.application.form.label.creationMoment" path="creationMoment"/>
	<acme:form-textbox code="worker.application.form.label.status" path="status"/>
	<acme:form-textbox code="worker.application.form.label.statement" path="statement"/>
	<acme:form-textarea code="worker.application.form.label.skills" path="skills"/>
	<acme:form-textarea code="worker.application.form.label.qualifications" path="qualifications"/>
	
	<acme:form-return code="worker.application.form.label.button.return"/>
</acme:form>