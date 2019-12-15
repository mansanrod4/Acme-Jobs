<%@page language="java"%>

<%@taglib prefix="jstl" uri= "http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form>
	<acme:form-url code="sponsor.non-commercial-banner.form.label.picture" path="picture" />
	<acme:form-url code="sponsor.non-commercial-banner.form.label.slogan" path="slogan" />
	<acme:form-url code="sponsor.non-commercial-banner.form.label.targetURL" path="targetURL" />
	<acme:form-url code="sponsor.non-commercial-banner.form.label.jingle" path="jingle" />

	<acme:form-submit test="${command == 'show' || command == 'update' || command == 'delete'}"
		code="sponsor.non-commercial-banner.form.button.update"
		action="/sponsor/non-commercial-banner/update"/>
		
	<acme:form-submit test="${command == 'show' || command == 'delete' || command == 'update'}"
		code="sponsor.non-commercial-banner.form.button.delete"
		action="/sponsor/non-commercial-banner/delete"/>
	
	<acme:form-submit test="${command == 'create'}"
		code="sponsor.non-commercial-banner.form.button.create"
		action="/sponsor/non-commercial-banner/create"/>
	
	<acme:form-return code="sponsor.non-commercial-banner.form.button.return"/>
</acme:form>