<%@page language="java"%>

<%@taglib prefix="jstl" uri= "http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form readonly="true">
	<acme:form-textbox code="authenticated.challenge.form.label.title" path="title"/>
	<acme:form-moment code="authenticated.challenge.form.label.moment" path="moment"/>
	<acme:form-textarea code="authenticated.challenge.form.label.description" path="description"/>
	<acme:form-moment code="authenticated.challenge.form.label.deadline" path="deadline"/>
	<acme:form-textbox code="authenticated.challenge.form.label.goalBronze" path="goalBronze"/>
	<acme:form-textbox code="authenticated.challenge.form.label.goalSilver" path="goalSilver"/>
	<acme:form-textbox code="authenticated.challenge.form.label.goalGold" path="goalGold"/>
	<acme:form-money code="authenticated.challenge.form.label.rewardBronze" path="rewardBronze"/>
	<acme:form-money code="authenticated.challenge.form.label.rewardSilver" path="rewardSilver"/>
	<acme:form-money code="authenticated.challenge.form.label.rewardGold" path="rewardGold"/>
	
	<acme:form-return code="authenticated.challenge.form.button.return" />	
</acme:form>