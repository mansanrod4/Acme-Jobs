<%@page language="java"%>

<%@taglib prefix="jstl" uri= "http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form>
	<acme:form-url code="administrator.commercial-banner.form.label.picture" path="picture"/>
	<acme:form-textarea code="administrator.commercial-banner.form.label.slogan" path="slogan"/>
	<acme:form-url code="administrator.commercial-banner.form.label.targetURL" path="targetURL"/>
	<acme:form-textbox code="administrator.commercial-banner.form.label.creditCardNumber" path="creditCardNumber"/>
	<acme:form-textbox code="administrator.commercial-banner.form.label.expirationDate" path="expirationDate" placeholder="mm/yy" /> 
	<acme:form-textbox code="administrator.commercial-banner.form.label.cvv" path="cvv" placeholder="XXX"/>
	
	<acme:form-submit test="${command == 'show'}"
		code="administrator.commercial-banner.form.button.update"
		action="/administrator/commercial-banner/update"/>
		
	<acme:form-submit test="${command == 'show'}"
		code="administrator.commercial-banner.form.button.delete"
		action="/administrator/commercial-banner/delete"/>
		
	<acme:form-submit test="${command == 'create'}"
		code="administrator.commercial-banner.form.button.create"
		action="/administrator/commercial-banner/create"/>
		
	<acme:form-submit test="${command == 'update'}"
		code="administrator.commercial-banner.form.button.update"
		action="/administrator/commercial-banner/update"/>
		
	<acme:form-submit test="${command == 'delete'}"
		code="administrator.commercial-banner.form.button.delete"
		action="/administrator/commercial-banner/delete"/>
	
	<acme:form-return 
		code="administrator.commercial-banner.form.button.return" />	
</acme:form>