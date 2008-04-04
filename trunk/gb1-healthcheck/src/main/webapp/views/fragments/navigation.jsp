<%@ taglib prefix="authz" uri="http://acegisecurity.org/authz"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<h3><s:text name="navigation.actions.title" /></h3>

<ul>
	<authz:authorize ifAllGranted="role_administrator">
		<li class="navAction">
			<a href='<s:url namespace="/users" action="manageUsers" />'><s:text name="navigation.actions.manageUsers" /></a>
		</li>
	</authz:authorize>
	<li class="navAction">
		<a href='<s:url namespace="/foods" action="manageFoods" />'><s:text name="navigation.actions.manageFoods" /></a>
	</li>
	<li class="navAction">
		<a href='<s:url namespace="/meals" action="manageMeals" />'><s:text name="navigation.actions.manageMeals" /></a>
	</li>
	<li class="navAction">
		<a href='<s:url namespace="/metrics" action="manageGastricStates" />'><s:text name="navigation.actions.manageMetrics" /></a>
	</li>
</ul>
