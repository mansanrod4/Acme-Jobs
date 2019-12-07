<%@page language="java"%>

<%@taglib prefix="jstl" uri= "http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:list>
	<acme:list-column code="authenticated.challenge.list.label.moment" path="moment" width="20%"/>
	<acme:list-column code="authenticated.challenge.list.label.title" path="title" width="50%"/>
	<acme:list-column code="authenticated.challenge.list.label.deadline" path="deadline" width="50%"/>
</acme:list>

