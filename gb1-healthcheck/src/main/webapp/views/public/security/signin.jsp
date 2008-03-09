<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<%@ page import="org.acegisecurity.ui.webapp.AuthenticationProcessingFilter" %>

<html>
	<body>
		<c:if test="${not empty param.login_error}">
			<p id="loginError">
				<s:text name="errors.signin.badcredentials" />
			</p>
		</c:if>

		<c:url var="actionUrl" value="/j_acegi_security_check" />
		<form id="signinForm" action="${actionUrl}">
			<table>
				<tr>
					<td><label class="required"><s:text name="signin.login" />:</label></td>
					<td>
						<input type="text" name="j_username"
							<c:if test="${not empty param.login_error}">
								value="<%= session.getAttribute(AuthenticationProcessingFilter.ACEGI_SECURITY_LAST_USERNAME_KEY) %>"
							</c:if>
						/>
					</td>
				</tr>
				<tr>
					<td><label class="required"><s:text name="signin.password" />:</label></td>
					<td><input type="password" name="j_password" /></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td>
						<input class="checkboxInput" type="checkbox" name="rememberMe" />
						<s:text name="signin.rememberMe" />
					</td>
				</tr>
			</table>

			<div class="actions">
				<input class="button" type="submit" value='<s:text name="signin.submit" />' />
			</div>
		</form>

		<ul id="signinActions">
			<li class="first"><a href='<s:url namespace="/public/register" action="request" />'><s:text name="signin.noAccountYet" /></a></li>
			<li class="last"><a href='<s:url namespace="/public/security" action="lostPassword" />'><s:text name="signin.lostPassword" /></a></li>
		</ul>
	</body>
</html>
