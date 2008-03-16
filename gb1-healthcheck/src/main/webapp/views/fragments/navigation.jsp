<%@ taglib prefix="authz" uri="http://acegisecurity.org/authz"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<h3><s:text name="navigation.actions.title" /></h3>

<ul>
	<authz:authorize ifAllGranted="role_administrator">
		<li class="navAction"><s:text name="navigation.actions.listUsers" /></li>
	</authz:authorize>
	<li class="navAction"><a href='<s:url namespace="/foods" action="list" />'><s:text name="navigation.actions.listFoods" /></a></li>
	<li class="navAction"><a href='<s:url namespace="/meals" action="list" />'><s:text name="navigation.actions.listMeals" /></a></li>
	<li class="navAction"><a href='<s:url namespace="/metrics" action="manage" />'><s:text name="navigation.actions.manageMetrics" /></a></li>
</ul>
