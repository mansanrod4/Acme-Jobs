
<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:list>
	<acme:list-column code="authenticated.userThread.list.label.username" path="authenticated.userAccount.username" />
</acme:list>

<jstl:if test="${creator == true }">
<button type="button" onclick="javascript: pushReturnUrl('/authenticated/userthread/list?id=${param.id}');
	redirect('/authenticated/authenticated/list-non-included?threadId=${param.id}')" class="btn btn-primary">
	<acme:message code="authenticated.userThread.list.button.NonIncludedUsers"/>
	</button>
</jstl:if>
<acme:form-return code="authenticated.userThread.list.button.return" />