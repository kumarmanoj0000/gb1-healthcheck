<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
	<body>
		<s:form action="sendPassword" method="post" namespace="/public/security">
			<s:textfield key="register.email" name="email" /><br/>
			<s:submit key="lostPassword.submit" />
		</s:form>
	</body>
</html>
