<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="authz" uri="http://acegisecurity.org/authz"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
	<body>
		<h2><fmt:message key="workbench.menu" /></h2>

		<authz:authorize ifAllGranted="role_administrator">
			<fmt:message key="workbench.menu.users.list" />
		</authz:authorize>

		<ul>
			<li><a href='<s:url namespace="/foods" action="list" />'><fmt:message key="workbench.menu.foods.list" /></a></li>
			<li><a href='<s:url namespace="/meals" action="list" />'><fmt:message key="workbench.menu.meals.list" /></a></li>
			<li><a href='<s:url namespace="/metrics" action="manage" />'><fmt:message key="workbench.menu.metrics.manage" /></a></li>
		</ul>
	</body>
</html>
