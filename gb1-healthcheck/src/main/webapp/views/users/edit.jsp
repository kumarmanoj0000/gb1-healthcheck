<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
	<body>
		<h1><fmt:message key="users.edit.title" /></h1>
		
		<c:url var="actionUrl" value="/users/editProfile.go" />
		<form:form id="form" method="post" action="${actionUrl}" >
			<form:hidden path="id" />
			<fmt:message key="users.edit.login" />: <c:out value="${command.login}" /><br />
			<fmt:message key="users.edit.email" />: <form:input path="email" /> <form:errors path="email" /> <br />
			<input type="submit" value='<fmt:message key="users.edit.submit" />' />
			<input type="submit" name="_cancel" value='<fmt:message key="users.edit.cancel" />' />
		</form:form>
	</body>
</html>
