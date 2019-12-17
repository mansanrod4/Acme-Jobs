<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form readonly="true">
	<acme:form-textbox code="authenticated.userThread.form.label.username" path="authenticated.userAccount.username" />
	<input type="hidden" name="threadId" id="threadId" value="${param.id}"/>
	
	<div class="collapse" id="alertDelete">
 	 <div class="alert alert-danger">
 		<acme:message code="authenticated.userThread.delete.notMe"/>
	</div>
	</div>
	
	<jstl:if test="${creatorThread == false }">
	<acme:form-submit code="authenticated.userThread.button.delete" action="/authenticated/userthread/delete"/>
	</jstl:if>
	
	<jstl:if test="${creatorThread == true }">
 	<button class="btn btn-primary" type="button" data-toggle="collapse" data-target="#alertDelete" aria-expanded="false" aria-controls="collapseExample">
  		<acme:message code="authenticated.userThread.button.delete"/>
  	</button>
  	</jstl:if>

	<acme:form-return code="authenticated.threads.form.button.return" />
</acme:form>