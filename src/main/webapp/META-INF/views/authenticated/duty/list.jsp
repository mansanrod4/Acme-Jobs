<%@page language="java"%>

<%@taglib prefix="jstl" uri= "http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:list>
	<acme:list-column code="authenticated.duty.list.label.title" path="title" width="80%"/>
	<acme:list-column code="authenticated.duty.list.label.percentageTimeWeek" path="percentageTimeWeek" width="20%"/>
</acme:list>