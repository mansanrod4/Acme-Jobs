<%--
- panic.jsp
-
- Copyright (c) 2019 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page language="java" isErrorPage="true"
	import="
		javax.servlet.jsp.ErrorData,
		org.apache.commons.logging.Log,
		org.apache.commons.logging.LogFactory,
		org.springframework.http.HttpStatus,
		acme.framework.helpers.ThrowableHelper,
		acme.framework.helpers.StringHelper,
		acme.framework.helpers.JspHelper"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<%
	Log log;
	Throwable oops;
	ErrorData errorData;
	
	oops = (Throwable)pageContext.getAttribute("oops", 2);
	errorData = pageContext.getErrorData();
	if (oops == null && errorData != null)
		oops = errorData.getThrowable();
			
	log = LogFactory.getLog(getClass());
	log.error(String.format("HTTP %s %s %s", request.getMethod(), request.getRequestURI(), request.getProtocol()));
	log.error(String.format("Status: %d", response.getStatus()));
	if (oops != null)
		log.error(String.format("Exception: %s", ThrowableHelper.toString(oops)));
	
	if (request.getServerName().equals("localhost") || request.getServerName().equals("127.0.0.1"))
		pageContext.setAttribute("oops", oops, 2);
	else
		pageContext.setAttribute("oops", null, 2);
	pageContext.setAttribute("method", request.getMethod());
	pageContext.setAttribute("url", JspHelper.getRequestUrl(request));
	pageContext.setAttribute("protocol", request.getProtocol());
	pageContext.setAttribute("status", response.getStatus());
	pageContext.setAttribute("reason", 
		HttpStatus.resolve(response.getStatus()) != null ? 
			HttpStatus.valueOf(response.getStatus()).getReasonPhrase() : "Unknown failure");
%>

<p>
	<acme:message code="master.panic.text"/>			
</p>

<dl>
	<dt>
		<acme:message code="master.panic.label.request"/>
	</dt>
	<dd>
		<jstl:out value="${method}"/>
		&nbsp;
		<jstl:out value="${url}"/>
		&nbsp;
		<jstl:out value="${protocol}"/>
	</dd>
	
	<dt>
		<acme:message code="master.panic.label.status"/>
	</dt>
	<dd>
		<jstl:out value="${status}"/>
		&nbsp;
		<jstl:out value="${reason}"/> 
	</dd>	
	<jstl:if test="${oops != null}">
		<dt>
			<acme:message code="master.panic.label.exceptions"/>
		</dt>
		<dd>
			<jstl:set var="current" value="${oops}"/>
			<jstl:forEach var="i" begin="1" end="100">
				<jstl:if test="${current != null}">
					<jstl:set var="oopsTitle" value="${ThrowableHelper.formatText(current.stackTrace[0].toString())}"/>
					<jstl:set var="oopsDescription" value="${ThrowableHelper.formatText(current.toString())}"/>
					<div>
  						<div class="text-danger" style="word-wrap: break-word;"><strong><jstl:out value="${oopsTitle}"/></strong></div>
  						<div>
  							<div style="float: left;">&#x2937;</div>
  							<div class="text-info" style="white-space: pre-wrap; margin-left: 2em;"><jstl:out value="${oopsDescription}"/></div>
  						</div>
					</div>
					<jstl:set var="current" value="${current.cause}"/>
				</jstl:if>
			</jstl:forEach>
			<jstl:if test="${current != null}">
				<acme:message code="master.panic.text.consult-log"/>
			</jstl:if>
		</dd>
	</jstl:if>
</dl>
