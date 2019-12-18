<%@page language="java"%>

<%@taglib prefix="jstl" uri= "http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:list>
	<acme:list-column code="sponsor.commercial-banner.list.label.picture" path="picture" width="20%"/>
	<acme:list-column code="sponsor.commercial-banner.list.label.targetURL" path="targetURL" width="20%"/>
	<acme:list-column code="sponsor.commercial-banner.list.label.slogan" path="slogan" width="60%"/>
</acme:list>