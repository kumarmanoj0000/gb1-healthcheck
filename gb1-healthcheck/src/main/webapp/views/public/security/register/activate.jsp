<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
	<body>
		<s:form action="activate" method="post" namespace="/public/register">
			<s:textfield key="register.activate.email" name="principal" /><br/>
			<s:textfield key="register.activate.activationToken" name="credentials" /><br/>
			<s:submit key="register.activate.submit" />
		</s:form>
	</body>
</html>
