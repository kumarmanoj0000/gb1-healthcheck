<%@ taglib prefix="authz" uri="http://acegisecurity.org/authz" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

<h1><fmt:message key="app.name" /></h1>

<authz:authorize ifNotGranted="role_anonymous">
	<div>
		<fmt:message key="header.userlogin" />: <authz:authentication operation="username" />
	</div>
	<ul id="headerActions">
		<li class="first"><a href="<c:url value='/users/editInput.go' />"><fmt:message key="header.profile" /></a></li>
		<li class="last"><a href="<c:url value='/public/security/signOff.go' />"><fmt:message key="header.logoff" /></a></li>
	</ul>
</authz:authorize>
