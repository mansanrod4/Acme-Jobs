<%@page language="java"%>

<%@taglib prefix="jstl" uri= "http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form>
	<acme:form-url code="sponsor.commercial-banner.form.label.picture" path="picture" />
	<acme:form-textarea code="sponsor.commercial-banner.form.label.slogan" path="slogan" />
	<acme:form-url code="sponsor.commercial-banner.form.label.targetURL" path="targetURL" />
	<acme:form-textbox code="sponsor.commercial-banner.form.label.creditCardHolder" path="creditCardHolder" readonly="${readOnly}"/>
	<acme:form-textbox code="sponsor.commercial-banner.form.label.creditCardNumber" path="creditCardNumber" readonly="${readOnly}"/>
	
	<jstl:if test="${(command == 'create')}">
		<acme:form-select code="sponsor.commercial-banner.form.label.brand" path="marca">
			<acme:form-option code="VISA" value="VISA" />
			<acme:form-option code="MasterCard" value="MASTERCARD"/>
			<acme:form-option code="American Express" value="AMERICAN_EXPRESS"/>
			<acme:form-option code="Diners Club" value="DINERS_CLUB"/>			
		</acme:form-select>	
	</jstl:if>
	
	<jstl:if test="${command == 'show' || command == 'update' || command == 'delete'}">
		<acme:form-textbox code="sponsor.commercial-banner.form.label.brand" path="marcaString" readonly ="${readOnly}" />
	</jstl:if>
	
	<acme:form-textbox code="sponsor.commercial-banner.form.label.expirationDate" path="expirationDate" placeholder="MM/YY" readonly="${readOnly}"/>
	<acme:form-textbox code="sponsor.commercial-banner.form.label.cvv" path="cvv" placeholder="999" readonly="${readOnly}"/>
	
	<acme:form-submit test="${command == 'show' || command == 'update' || command == 'delete'}"
		code="sponsor.commercial-banner.form.button.update"
		action="/sponsor/commercial-banner/update"/>
		
	<acme:form-submit test="${command == 'show' || command == 'delete' || command == 'update'}"
		code="sponsor.commercial-banner.form.button.delete"
		action="/sponsor/commercial-banner/delete"/>
	
	<acme:form-submit test="${command == 'create'}"
		code="sponsor.commercial-banner.form.button.create"
		action="/sponsor/commercial-banner/create"/>
	
	<acme:form-return code="sponsor.commercial-banner.form.button.return"/>
</acme:form>