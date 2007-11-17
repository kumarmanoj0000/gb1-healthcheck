<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="authz" uri="http://acegisecurity.org/authz"%>

<html>
	<body>

		<c:if test="${not empty notificationMessage}">
			<div class="notification"><fmt:message key="${notificationMessage}" /></div>
		</c:if>

		<h2><fmt:message key="workbench.menu" /></h2>

		<authz:authorize ifAllGranted="role_administrator">
			<fmt:message key="workbench.menu.users.list" />
		</authz:authorize>

		<ul>
			<li><a href='<c:url value="/foods/list.go" />'><fmt:message key="workbench.menu.foods.list" /></a></li>
			<li><a href='<c:url value="/meals/list.go" />'><fmt:message key="workbench.menu.meals.list" /></a></li>
			<li><a href='<c:url value="/metrics/manage.go" />'><fmt:message key="workbench.menu.metrics.manage" /></a></li>
		</ul>
	</body>
</html>
