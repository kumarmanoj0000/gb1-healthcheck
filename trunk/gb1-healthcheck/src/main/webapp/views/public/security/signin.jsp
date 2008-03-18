<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
	<body>
		<s:if test="#parameters['login_error'] != null">
			<p id="loginError"><s:text name="errors.signin.badcredentials" /></p>
		</s:if>

		<form id="signinForm" action="<%= request.getContextPath() %>/j_acegi_security_check">
			<table>
				<tr>
					<td><label class="required"><s:text name="signin.login" />:</label></td>
					<td><s:textfield name="j_username" value="%{#session['ACEGI_SECURITY_LAST_USERNAME']}" /></td>
				</tr>
				<tr>
					<td><label class="required"><s:text name="signin.password" />:</label></td>
					<td><s:password name="j_password" /></td>
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
				<s:submit cssClass="button" key="signin.submit" />
			</div>
		</form>

		<ul id="signinActions">
			<li class="first"><a href='<s:url namespace="/public/register" action="request" />'><s:text name="signin.noAccountYet" /></a></li>
			<li class="last"><a href='<s:url namespace="/public/security" action="lostPassword" />'><s:text name="signin.lostPassword" /></a></li>
		</ul>
	</body>
</html>
