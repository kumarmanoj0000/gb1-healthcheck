<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
	<body>
		<s:actionerror/>
		<s:form action="register" method="post" namespace="/public/register">
			<s:textfield key="register.login" name="model.login" /><br/>
			<s:textfield key="register.email" name="model.email" /><br/>
			<s:password key="register.password1" name="model.password1" /><br/>
			<s:password key="register.password2" name="model.password2" /><br/>
			<s:submit key="register.submit" />
		</s:form>
	</body>
</html>
