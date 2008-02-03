<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
	<body>
		<s:actionerror/>
		<s:form action="register" method="post" namespace="/public/register">
			<div class="required">
				<label><fmt:message key="register.login" /></label>
				<s:textfield name="model.login" />
			</div>

			<div class="required">
				<label><fmt:message key="register.email" /></label>
				<s:textfield name="model.email" /><br/>
			</div>

			<div class="required">
				<label><fmt:message key="register.password1" /></label>
				<s:password name="model.password1" /><br/>
			</div>

			<div class="required">
				<label><fmt:message key="register.password2" /></label>
				<s:password name="model.password2" /><br/>
			</div>

			<s:submit key="register.submit" />
		</s:form>
	</body>
</html>
