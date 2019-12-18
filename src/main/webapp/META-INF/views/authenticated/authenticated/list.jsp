

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:list readonly="true">
	<acme:list-column code="authenticated.authenticated.list.label.username" path="userAccount.username" />
</acme:list>

<jstl:if test="${model$size>0}">
	<acme:form readonly="false">
		<acme:form-select readonly="false" path="idAuthenticated" code="authenticated.authenticated.form.select">

			<jstl:if test="${model$size>1}">
				<jstl:forEach var="var" begin="${0}" end="${model$size-1}">
					<jstl:set var="var1" value="id[${var}]" />
					<jstl:set var="var2" value="userAccount.username[${var}]" />
					<acme:form-option code="${requestScope[var2]}" value="${requestScope[var1]}" />
				</jstl:forEach>
			</jstl:if>

			<jstl:if test="${model$size == 1}">
				<jstl:set var="var1" value="userAccount.username"/>
				<acme:form-option code="${requestScope[var1]}" value="${id}" />
			</jstl:if>

		</acme:form-select>

		<acme:form-submit code="authenticated.userThread.list.button.createIncludedUsers"
			action="/authenticated/userthread/create?threadId=${param.threadId}" />
			
	</acme:form>
</jstl:if>



<acme:form-return code="authenticated.authenticated.list.button.return" />