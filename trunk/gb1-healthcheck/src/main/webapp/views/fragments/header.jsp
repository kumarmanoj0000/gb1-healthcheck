<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<script type="text/javascript">
	function changeLanguage(language) {
		window.location.href = '${request.requestURL}?request_locale=' + language;
	}
</script>

<h1><s:text name="app.name" /></h1>

<security:authorize ifAnyGranted="ROLE_STANDARD,ROLE_ADMINISTRATOR">
	<div>
		<s:text name="header.userlogin" />: <security:authentication property="principal.username" />
	</div>

	<ul id="headerActions">
		<li class="first">
			<s:select
				name="language" list="#{'en' : 'English', 'fr' : 'Français'}" value="%{#session['WW_TRANS_I18N_LOCALE'].language}"
				onchange="changeLanguage(this.value)"
			/>
		</li>
		<li>
			<a href="<s:url namespace='/users' action='editOwnUser' method='input' />"><s:text name="header.profile" /></a>
		</li>
		<li class="last">
			<a href="<s:url namespace='/public/security' action='logout' />"><s:text name="header.logout" /></a>
		</li>
	</ul>
</security:authorize>
