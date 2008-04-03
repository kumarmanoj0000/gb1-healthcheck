<%@ taglib prefix="authz" uri="http://acegisecurity.org/authz" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<script type="text/javascript">
	function changeLanguage(language) {
		window.location.href = '${request.requestURL}?request_locale=' + language;
	}
</script>

<h1><s:text name="app.name" /></h1>

<authz:authorize ifNotGranted="role_anonymous">
	<div>
		<s:text name="header.userlogin" />: <authz:authentication operation="username" />
	</div>

	<ul id="headerActions">
		<li class="first">
			<s:select
				name="language" list="#{'en' : 'English', 'fr' : 'Français'}" value="%{#session['WW_TRANS_I18N_LOCALE'].language}"
				onchange="changeLanguage(this.value)"
			/>
		</li>
		<li>
			<a href="<s:url namespace='/users' action='editUser' method='input' />"><s:text name="header.profile" /></a>
		</li>
		<li class="last">
			<a href="<s:url namespace='/public/security' action='signOff' />"><s:text name="header.logoff" /></a>
		</li>
	</ul>
</authz:authorize>
