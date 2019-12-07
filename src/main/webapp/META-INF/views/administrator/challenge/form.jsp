<%@page language="java"%>

<%@taglib prefix="jstl" uri= "http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form >
	<acme:form-textbox code="administrator.challenge.form.label.title" path="title"/>
	<jstl:if test="${command != 'create'}">
		<acme:form-moment 
			code="administrator.challenge.form.label.moment"
			path="moment"
			readonly="true"/>
	</jstl:if>
	<acme:form-moment code="administrator.challenge.form.label.deadline" path="deadline"/>
	<acme:form-textarea code="administrator.challenge.form.label.description" path="description"/>
	
	<acme:form-textarea code="administrator.challenge.form.label.goalBronze" path="goalBronze"/>
		<acme:form-money code="administrator.challenge.form.label.rewardBronze" path="rewardBronze"/>
		
	<acme:form-textarea code="administrator.challenge.form.label.goalSilver" path="goalSilver"/>
	<acme:form-money code="administrator.challenge.form.label.rewardSilver" path="rewardSilver"/>
	
	<acme:form-textarea code="administrator.challenge.form.label.goalGold" path="goalGold"/>
	<acme:form-money code="administrator.challenge.form.label.rewardGold" path="rewardGold"/>
	
	<acme:form-submit test="${command == 'show'}"
		code="administrator.challenge.form.button.update"
		action="/administrator/challenge/update"/>
		
	<acme:form-submit test="${command == 'show'}"
		code="administrator.challenge.form.button.delete"
		action="/administrator/challenge/delete"/>
		
	<acme:form-submit test="${command == 'create'}"
		code="administrator.challenge.form.button.create"
		action="/administrator/challenge/create"/>
		
	<acme:form-submit test="${command == 'update'}"
		code="administrator.challenge.form.button.update"
		action="/administrator/challenge/update"/>
		
	<acme:form-submit test="${command == 'delete'}"
		code="administrator.announcement.form.button.delete"
		action="/administrator/announcement/delete"/>
	
	
	
	
	<acme:form-return code="administrator.challenge.form.button.return" />	
</acme:form>