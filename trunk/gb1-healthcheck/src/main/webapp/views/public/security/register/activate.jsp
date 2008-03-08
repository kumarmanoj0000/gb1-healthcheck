<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
	<body>
		<s:actionerror />

		<s:form action="activate" namespace="/public/register">
			<div class="required">
				<label><fmt:message key="register.activate.email" />:</label>
				<s:textfield name="principal" />
				<s:fielderror><s:param>principal</s:param></s:fielderror>
			</div>

			<div class="required">
				<label><fmt:message key="register.activate.activationToken" />:</label>
				<s:textfield name="credentials" />
				<s:fielderror><s:param>credentials</s:param></s:fielderror>
			</div>

			<s:submit key="register.activate.submit" />
		</s:form>
	</body>
</html>
