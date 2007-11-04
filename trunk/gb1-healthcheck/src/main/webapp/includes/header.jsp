<%@ taglib prefix="authz" uri="http://acegisecurity.org/authz" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

<h1><fmt:message key="app.name" /></h1>
<authz:authorize ifNotGranted="role_anonymous">
	<div>
		<fmt:message key="header.userlogin" />: <authz:authentication operation="username" />
	</div>
	<ul>
		<li><a href="<c:url value="/public/security/signOff.go" />"><fmt:message key="header.logoff" /></a></li>
	</ul>
</authz:authorize>
