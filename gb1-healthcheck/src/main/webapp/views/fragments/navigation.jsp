<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<h2><s:text name="navigation.actions.title" /></h2>

<ul>
	<security:authorize ifAllGranted="ROLE_ADMINISTRATOR">
		<li class="navAction">
			<s:url id="manageUsersUrl" namespace="/admin/users" action="manageUsers">
				<s:param name="refreshList" value="true" />
			</s:url>
			<a href='${manageUsersUrl}'><s:text name="navigation.actions.manageUsers" /></a>
		</li>
	</security:authorize>
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
