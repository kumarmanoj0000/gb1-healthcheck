<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="authz" uri="http://acegisecurity.org/authz"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
	<body>
		<%@ include file="/views/fragments/actionMessages.jsp"%>

		<h2><s:text name="workbench.menu" /></h2>

		<authz:authorize ifAllGranted="role_administrator">
			<s:text name="workbench.menu.users.list" />
		</authz:authorize>

		<ul>
			<li><a href='<s:url namespace="/foods" action="list" />'><s:text name="workbench.menu.foods.list" /></a></li>
			<li><a href='<s:url namespace="/meals" action="list" />'><s:text name="workbench.menu.meals.list" /></a></li>
			<li><a href='<s:url namespace="/metrics" action="manage" />'><s:text name="workbench.menu.metrics.manage" /></a></li>
		</ul>
	</body>
</html>
