<%@ taglib prefix="authz" uri="http://acegisecurity.org/authz"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<h3><s:text name="navigation.actions.title" /></h3>

<ul>
	<authz:authorize ifAllGranted="role_administrator">
		<li class="navAction">
			<a href='<s:url namespace="/users" action="listUsers" />'><s:text name="navigation.actions.listUsers" /></a>
		</li>
	</authz:authorize>
	<li class="navAction">
		<a href='<s:url namespace="/foods" action="listFoods" />'><s:text name="navigation.actions.listFoods" /></a>
	</li>
	<li class="navAction">
		<a href='<s:url namespace="/meals" action="listMeals" />'><s:text name="navigation.actions.listMeals" /></a>
	</li>
	<li class="navAction">
		<a href='<s:url namespace="/metrics" action="manageGastricStates" />'><s:text name="navigation.actions.manageMetrics" /></a>
	</li>
</ul>
