<%@page language="java"%>

<%@taglib prefix="jstl" uri= "http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form>
	<acme:form-textbox code="consumer.offer.form.label.title" path="title"/>
	
	<jstl:if test="${command != 'create'}">		
		<acme:form-moment 
		code="consumer.offer.form.label.moment" 
		path="moment"
		readonly= "true"/>
	</jstl:if>
	
	<acme:form-moment code="consumer.offer.form.label.deadLine" path="deadLine"/>
	<acme:form-textarea code="consumer.offer.form.label.text" path="text"/>
	<acme:form-money code="consumer.offer.form.label.money" path="money"/>
	<acme:form-textbox code="consumer.offer.form.label.ticker" path="ticker"/>
	<acme:form-checkbox code="consumer.offer.form.label.accept" path="accept"/>
	
	<acme:form-submit test="${command == 'create'}"
		code="consumer.offer.form.button.create"
		action="/consumer/offer/create"/>
	
	<acme:form-return 
	code="consumer.offer.form.button.return" />	
	
</acme:form>