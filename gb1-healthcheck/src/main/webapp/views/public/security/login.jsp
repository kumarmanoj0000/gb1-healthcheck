<%@ taglib prefix="s" uri="/struts-tags" %>

<h2><s:text name="login.welcome" /></h2>

<s:if test="#parameters['login_error'] != null">
	<p id="loginError"><s:text name="errors.login.badcredentials" /></p>
</s:if>

<form id="loginForm" action="<%= request.getContextPath() %>/j_acegi_security_check">
	<table>
		<tr>
			<td><label class="required"><s:text name="user.login" />:</label></td>
			<td><s:textfield name="j_username" value="%{#session['ACEGI_SECURITY_LAST_USERNAME']}" /></td>
		</tr>
		<tr>
			<td><label class="required"><s:text name="user.password" />:</label></td>
			<td><s:password name="j_password" /></td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td>
				<input class="checkboxInput" type="checkbox" name="rememberMe" />
				<s:text name="login.rememberMe" />
			</td>
		</tr>
	</table>

	<div class="actions">
		<s:submit cssClass="button" key="login.submit" />
	</div>
</form>

<ul id="loginActions">
	<li class="first"><a href='<s:url namespace="/public/register" action="registerUser" method="input" />'><s:text name="login.noAccountYet" /></a></li>
	<li class="last"><a href='<s:url namespace="/public/security" action="lostPassword" method="input" />'><s:text name="login.lostPassword" /></a></li>
</ul>
