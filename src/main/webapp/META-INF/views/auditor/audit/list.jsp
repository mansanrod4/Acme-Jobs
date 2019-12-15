<%@page language="java"%>

<%@taglib prefix="jstl" uri= "http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:list>
	<acme:list-column code="auditor.audit.list.label.me" path="me" width="5%"/>
	<acme:list-column code="auditor.audit.list.label.identity.name" path="identity.name" width="25%"/>
	<acme:list-column code="auditor.audit.list.label.title" path="title" width="20%"/>
	<acme:list-column code="auditor.audit.list.label.moment" path="moment" width="30%"/>
	<acme:list-column code="auditor.audit.list.label.status" path="status" width="20%"/>
</acme:list>