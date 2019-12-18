
<%--
- list.jsp
-
- Copyright (c) 2019 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:list >
	<acme:list-column code="authenticated.message.list.label.title" path="title" width="25%" />
	<acme:list-column code="authenticated.message.list.label.moment" path="moment" width="25%"/>
	<acme:list-column code="authenticated.message.list.label.tags" path="tags" width="25%"/>
	<acme:list-column code="authenticated.message.list.label.author" path="authorName" width="25%"/>
	
</acme:list>

<button type="button" onclick="javascript: pushReturnUrl('/authenticated/message/list?id=${param.id}'); redirect('/authenticated/message/create?threadId=${param.id}')" class="btn btn-primary">
		<acme:message code="authenticated.message.list.button.post" />
	</button>
	
	<acme:form-return code="authenticated.message.list.button.return" />



