
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
	<acme:list-column code="authenticated.messagethread.list.label.title" path="title" width="33%"/>
	<acme:list-column code="authenticated.messagethread.list.label.moment" path="moment" width="33%"/>
	<acme:list-column code="authenticated.messagethread.list.label.author" path="authorName" width="33%"/>
</acme:list>


