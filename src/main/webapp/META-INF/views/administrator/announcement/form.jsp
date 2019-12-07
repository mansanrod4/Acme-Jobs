<%@page language="java"%>

<%@taglib prefix="jstl" uri= "http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form>
	<acme:form-textbox code="administrator.announcement.form.label.title" path="title"/>
	
	<jstl:if test="${command != 'create'}">
		<acme:form-moment 
		code="administrator.announcement.form.label.moment" 
		path="moment"
		readonly= "true"/>
	</jstl:if>

	<acme:form-url code="administrator.announcement.form.label.moreInfo" path="moreInfo"/>
	<acme:form-textarea code="administrator.announcement.form.label.text" path="text"/>
	
	<acme:form-submit test="${command == 'show'}"
		code="administrator.announcement.form.button.update"
		action="/administrator/announcement/update"/>
		
	<acme:form-submit test="${command == 'show'}"
		code="administrator.announcement.form.button.delete"
		action="/administrator/announcement/delete"/>
		
	<acme:form-submit test="${command == 'create'}"
		code="administrator.announcement.form.button.create"
		action="/administrator/announcement/create"/>
		
	<acme:form-submit test="${command == 'update'}"
		code="administrator.announcement.form.button.update"
		action="/administrator/announcement/update"/>
		
	<acme:form-submit test="${command == 'delete'}"
		code="administrator.announcement.form.button.delete"
		action="/administrator/announcement/delete"/>
	
	<acme:form-return 
		code="administrator.announcement.form.button.return" />	
</acme:form>