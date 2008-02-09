<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
	<body>
		<s:actionerror/>

		<s:form action="register" namespace="/public/register">
			<div class="required">
				<label><fmt:message key="register.login" />:</label>
				<s:textfield name="model.login" />
				<s:fielderror><s:param>model.login</s:param></s:fielderror>
			</div>

			<div class="required">
				<label><fmt:message key="register.email" />:</label>
				<s:textfield name="model.email" />
				<s:fielderror><s:param>model.email</s:param></s:fielderror>
			</div>

			<div class="required">
				<label><fmt:message key="register.password1" />:</label>
				<s:textfield name="model.password1" />
				<s:fielderror><s:param>model.password1</s:param></s:fielderror>
			</div>

			<div class="required">
				<label><fmt:message key="register.password2" />:</label>
				<s:textfield name="model.password2" />
				<s:fielderror><s:param>model.password2</s:param></s:fielderror>
			</div>

			<div class="actions">
				<s:submit key="register.submit" />
			</div>
		</s:form>
	</body>
</html>
