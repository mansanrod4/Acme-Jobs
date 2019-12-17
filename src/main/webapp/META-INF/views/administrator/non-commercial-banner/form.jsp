<%@page language="java"%>

<%@taglib prefix="jstl" uri= "http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form>
	<acme:form-url code="administrator.non-commercial-banner.form.label.picture" path="picture"/>
	<acme:form-textarea code="administrator.non-commercial-banner.form.label.slogan" path="slogan"/>
	<acme:form-url code="administrator.non-commercial-banner.form.label.targetURL" path="targetURL"/>
	<acme:form-url code="administrator.non-commercial-banner.form.label.jingle" path="jingle"/>
	
	<acme:form-submit test="${command == 'show'}"
		code="administrator.non-commercial-banner.form.button.update"
		action="/administrator/non-commercial-banner/update"/>
		
	<acme:form-submit test="${command == 'show'}"
		code="administrator.commercial-banner.form.button.delete"
		action="/administrator/non-commercial-banner/delete"/>
		
	<acme:form-submit test="${command == 'create'}"
		code="administrator.non-commercial-banner.form.button.create"
		action="/administrator/non-commercial-banner/create"/>
		
	<acme:form-submit test="${command == 'update'}"
		code="administrator.non-commercial-banner.form.button.update"
		action="/administrator/non-commercial-banner/update"/>
		
	<acme:form-submit test="${command == 'delete'}"
		code="administrator.non-commercial-banner.form.button.delete"
		action="/administrator/non-commercial-banner/delete"/>
	
	<acme:form-return 
		code="administrator.non-commercial-banner.form.button.return" />	
</acme:form>