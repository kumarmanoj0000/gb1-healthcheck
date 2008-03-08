<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
	<body>
		<h2><fmt:message key="users.edit.title" /></h2>
		
		<s:actionerror />

		<s:form action="editSubmit.go" namespace="/users">
			<s:hidden name="user.id" />

			<div class="required">
				<label><fmt:message key="users.edit.email" />:</label>
				<s:textfield name="model.email" />
				<s:fielderror><s:param>model.email</s:param></s:fielderror>
			</div>

			<div class="actions">
				<s:submit cssClass="button" key="users.edit.submit" />
				<s:submit cssClass="button" key="users.edit.cancel" name="method:cancel" />
			</div>
		</s:form>
	</body>
</html>
