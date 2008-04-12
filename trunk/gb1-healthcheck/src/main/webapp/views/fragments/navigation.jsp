<%@ taglib prefix="authz" uri="http://acegisecurity.org/authz"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<h3><s:text name="navigation.actions.title" /></h3>

<ul>
	<authz:authorize ifAllGranted="role_administrator">
		<li class="navAction">
			<s:url id="manageUsersUrl" namespace="/users" action="manageUsers">
				<s:param name="refreshList" value="true" />
			</s:url>
			<a href='${manageUsersUrl}'><s:text name="navigation.actions.manageUsers" /></a>
		</li>
	</authz:authorize>
	<li class="navAction">
		<s:url id="manageFoodsUrl" namespace="/foods" action="manageFoods">
				<s:param name="refreshList" value="true" />
		</s:url>
		<a href='${manageFoodsUrl}'><s:text name="navigation.actions.manageFoods" /></a>
	</li>
	<li class="navAction">
		<s:url id="manageMealsUrl" namespace="/meals" action="manageMeals">
				<s:param name="refreshList" value="true" />
		</s:url>
		<a href='${manageMealsUrl}'><s:text name="navigation.actions.manageMeals" /></a>
	</li>
	<li class="navAction">
		<a href='<s:url namespace="/metrics" action="manageGastricStates" />'><s:text name="navigation.actions.manageMetrics" /></a>
	</li>
</ul>
