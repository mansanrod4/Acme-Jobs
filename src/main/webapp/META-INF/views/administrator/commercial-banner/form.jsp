<%@page language="java"%>

<%@taglib prefix="jstl" uri= "http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form>
	<acme:form-url code="administrator.commercial-banner.form.label.picture" path="picture"/>
	<acme:form-textarea code="administrator.commercial-banner.form.label.slogan" path="slogan"/>
	<acme:form-url code="administrator.commercial-banner.form.label.targetURL" path="targetURL"/>
	<acme:form-textbox code="administrator.commercial-banner.form.label.creditCardHolder" path="creditCardHolder"/>
	<acme:form-textbox code="administrator.commercial-banner.form.label.creditCardNumber" path="creditCardNumber"/>
	
	<jstl:if test="${(command == 'create')}">
		<acme:form-select code="administrator.user-account.form.label.new-status" path="newStatus">
			<acme:form-option code="VISA" value="VISA" selected="true"/>
			<acme:form-option code="MasterCard" value="MASTERCARD"/>
			<acme:form-option code="American Express" value="AMERICAN_EXPRESS"/>
			<acme:form-option code="Diners Club" value="DINERS_CLUB"/>			
		</acme:form-select>	
	</jstl:if>
	
	<jstl:if test="${command == 'show' || command == 'update' || command == 'delete'}">
		<acme:form-textbox code="administrator.commercial-banner.form.label.creditCardMarca" path="marca" readonly ="true" />
	</jstl:if>
	
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