<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
	<body>
		<s:form action="sendPassword" method="post" namespace="/public/security">
			<div class="required">
				<label><fmt:message key="register.email" />:</label>
				<s:textfield name="email" />
			</div>

			<div class="actions">
				<s:submit key="lostPassword.submit" />
			</div>
		</s:form>
	</body>
</html>
