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

	<acme:form-textbox code="authenticated.messagethread.form.lable.title" path="title"/>
	
	<jstl:if test="${command != 'create'}">
		<acme:form-moment 
		code="authenticated.messagethread.form.label.moment" 
		path="moment"
		readonly= "true"/>
		<acme:form-moment 
		code="authenticated.messagethread.form.label.author" 
		path="authorName"
		readonly= "true"/>
	</jstl:if>
	
	<acme:form-submit test="${command == 'show'}"
		code="authenticated.messagethread.form.button.update"
		action="/authenticated/messagethread/update"/>
		
	<acme:form-submit test="${command == 'show'}"
		code="authenticated.messagethread.form.button.delete"
		action="/authenticated/messagethread/delete"/>
		
	<acme:form-submit test="${command == 'create'}"
		code="authenticated.messagethread.form.button.create"
		action="/authenticated/messagethread/create"/>
		
	<acme:form-submit test="${command == 'update'}"
		code="authenticated.messagethread.form.button.update"
		action="/authenticated/messagethread/update"/>
		
	<acme:form-submit test="${command == 'delete'}"
		code="authenticated.messagethread.form.button.delete"
		action="/authenticated/messagethread/delete"/>
	
	
  	<acme:form-submit 
		method="get" 
		code="authenticated.messagethread.form.button.message" 
		action="/authenticated/message/list?id=${id}"/>
	<acme:form-return code="authenticated.messagethread.form.button.return"/>
</acme:form>
