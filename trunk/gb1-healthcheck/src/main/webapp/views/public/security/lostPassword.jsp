<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
	<body>
		<s:actionerror />

		<s:form namespace="/public/security" action="sendPassword">
			<div class="required">
				<label><s:text name="register.email" />:</label>
				<s:textfield name="email" />
				<s:fielderror><s:param>email</s:param></s:fielderror>
			</div>

			<div class="actions">
				<s:submit key="lostPassword.submit" />
			</div>
		</s:form>
	</body>
</html>
