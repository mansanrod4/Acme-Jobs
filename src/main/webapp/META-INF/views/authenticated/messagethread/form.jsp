<%--
- form.jsp
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

<acme:form>
	<acme:form-textbox code="authenticated.messagethread.form.label.title" path="title" />
	<jstl:if test="${command == 'show' }">
	<acme:form-moment code="authenticated.messagethread.form.label.moment" path="moment" />
	<acme:form-textbox code="authenticated.messagethread.form.label.author" path="authorName" />
	
	
	<button type="button" onclick="javascript: pushReturnUrl('/authenticated/messagethread/show?id=${id}');
	redirect('/authenticated/message/list?id=${id}')" class="btn btn-primary">
	<acme:message code="authenticated.messagethread.form.label.message"/>
	</button>
	</jstl:if>
	<jstl:if test="${command == 'show'}">
	<button type="button" onclick="javascript: pushReturnUrl('/authenticated/messagethread/show?id=${id}');
	redirect('/authenticated/userthread/list?id=${id}')" class="btn btn-primary">
	<acme:message code="authenticated.messagethread.form.label.users"/>
	</button>
	</jstl:if>
	
	
		
	<acme:form-submit test="${command == 'create' }" code="authenticated.messagethread.form.button.create" action="/authenticated/messagethread/create"/>
	<acme:form-return code="authenticated.messagethread.form.button.return" />
</acme:form>