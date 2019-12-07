<%@page language="java"%>

<%@taglib prefix="jstl" uri= "http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form>
	<acme:form-textbox code="administrator.sysconfig.form.label.spamwords" path="spamwords"/>
	<acme:form-textbox code="administrator.sysconfig.form.label.threshold" path="threshold"/>
	
	<acme:form-submit test="${command == 'show'}"
		code="administrator.sysconfig.form.button.update"
		action="/administrator/sysconfig/update"/>
	
	<acme:form-submit test="${command == 'update'}"
		code="administrator.sysconfig.form.button.update"
		action="/administrator/sysconfig/update"/>
	
	<acme:form-return code="administrator.sysconfig.form.button.return" />	
</acme:form>