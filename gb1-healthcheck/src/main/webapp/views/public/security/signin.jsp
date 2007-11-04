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
		<form id="signinForm" method="post" action="${actionUrl}">
			<div class="required">
				<label><fmt:message key="signin.login" />:</label>
				<input type="text" name="j_username"
					<c:if test="${not empty param.login_error}">
						value="<%= session.getAttribute(AuthenticationProcessingFilter.ACEGI_SECURITY_LAST_USERNAME_KEY) %>"
					</c:if>
				/>
			</div>

			<div class="required">
				<label><fmt:message key="signin.password" />:</label>
				<input type="password" name="j_password" />
			</div>

			<div class="optional">
				<label class="checkboxLabel">
					<input class="checkboxInput" type="checkbox" name="rememberMe" />
					<fmt:message key="signin.rememberMe" />
				</label>
			</div>

			<div class="actions">
				<input class="button" type="submit" value='<fmt:message key="signin.submit" />' />
			</div>
		</form>

		<ul id="signinActions">
			<li class="first"><a href='<c:url value="/public/register/request.go" />'><fmt:message key="signin.noAccountYet" /></a></li>
			<li class="last"><a href='<c:url value="/public/security/lostPassword.go" />'><fmt:message key="signin.lostPassword" /></a></li>
		</ul>
	</body>
</html>
