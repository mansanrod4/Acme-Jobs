<%@page language="java"%>

<%@taglib prefix="jstl" uri= "http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:list>
	<acme:list-column code="authenticated.investor.list.label.sector" path="sector" width="20%"/>
	<acme:list-column code="authenticated.investor.list.label.name" path="name" width="20%"/>
	<acme:list-column code="authenticated.investor.list.label.star" path="star" width="20%"/>
</acme:list>

