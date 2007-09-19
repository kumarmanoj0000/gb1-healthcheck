<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="authz" uri="http://acegisecurity.org/authz"%>

<html>
	<body>

		<c:if test="${not empty notificationMessage}">
			<div class="notification"><fmt:message key="${notificationMessage}" /></div>
		</c:if>

		<h1><fmt:message key="workbench.welcome" /></h1>

		<authz:authorize ifAllGranted="ROLE_ADMINISTRATOR">
			<div class="menu">
				<fmt:message key="workbench.menu.userManagement.title" /><p/>
				<fmt:message key="workbench.menu.userManagement.listUsers" />
			</div>
		</authz:authorize>
	</body>
</html>
