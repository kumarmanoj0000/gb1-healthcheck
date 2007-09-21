<%@ taglib prefix="authz" uri="http://acegisecurity.org/authz" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

<div>
	<fmt:message key="app.header" /><br />
	<authz:authorize ifNotGranted="role_anonymous">
		<fmt:message key="header.userlogin" />: <authz:authentication operation="username" />
		<br />
		<a href="<c:url value="/users/editProfile.go?id=${user.id}" />"><fmt:message key="header.profile" /></a>
		<br />
		<a href="<c:url value="/public/security/signOff.go" />"><fmt:message key="header.logoff" /></a>
	</authz:authorize>
</div>
