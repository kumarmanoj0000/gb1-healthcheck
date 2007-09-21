<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="authz" uri="http://acegisecurity.org/authz"%>

<html>
	<body>

		<c:if test="${not empty notificationMessage}">
			<div class="notification"><fmt:message key="${notificationMessage}" /></div>
		</c:if>

		<h1><fmt:message key="workbench.welcome" /></h1>

		<authz:authorize ifAllGranted="role_administrator">
			<h2><fmt:message key="workbench.menu.userManagement.title" /></h2>
			<fmt:message key="workbench.menu.userManagement.listUsers" />
		</authz:authorize>

		<h2><fmt:message key="workbench.menu.foodManagement.title" /></h2>
		<a href='<c:url value="/nutrition/listFoods.go" />'><fmt:message key="workbench.menu.foodManagement.listFoods" /></a>
	</body>
</html>
