<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

<%@ page import="org.acegisecurity.ui.webapp.AuthenticationProcessingFilter" %>

<html>
	<body>
		<c:if test="${not empty param.login_error}">
			<p id="loginError">
				<fmt:message key="errors.signin.badcredentials" />
			</p>
		</c:if>

		<c:url var="actionUrl" value="/j_acegi_security_check" />
		<form id="loginForm" method="post" action="${actionUrl}">
			<fmt:message key="signin.login" />:
			<input type="text" name="j_username"
				<c:if test="${not empty param.login_error}">
					value="<%= session.getAttribute(AuthenticationProcessingFilter.ACEGI_SECURITY_LAST_USERNAME_KEY) %>"
				</c:if>
			/>
			<br />
			<fmt:message key="signin.password" />: <input type="password" name="j_password" />
			<br />
			<input type="checkbox" name="rememberMe" /> <fmt:message key="signin.rememberMe" />
			<br />
			<input type="submit" value='<fmt:message key="signin.submit" />' />
		</form>

		<p />
		<a href='<c:url value="/public/register/request.go" />'><fmt:message key="signin.noAccountYet" /></a>
		<br />
		<a href='<c:url value="/public/security/lostPassword.go" />'><fmt:message key="signin.lostPassword" /></a>
	</body>
</html>
