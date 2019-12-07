<%@page language="java"%>

<%@taglib prefix="jstl" uri= "http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:list>
	<acme:list-column code="worker.application.list.label.referenceNumber" path="referenceNumber" width="10%"/>
	<acme:list-column code="worker.application.list.label.job" path="job" width="10%"/>
	<acme:list-column code="worker.application.list.label.creationMoment" path="creationMoment" width="10%"/>
	<acme:list-column code="worker.application.list.label.statement" path="statement" width="60%"/>
	<acme:list-column code="worker.application.list.label.status" path="status" width="10%"/>
</acme:list>