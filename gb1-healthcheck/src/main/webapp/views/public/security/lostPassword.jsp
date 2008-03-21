<%@ taglib prefix="s" uri="/struts-tags"%>

<s:actionerror />

<s:form namespace="/public/security" action="lostPassword">
	<div class="required">
		<label><s:text name="register.email" />:</label>
		<s:textfield name="email" />
		<s:fielderror><s:param>email</s:param></s:fielderror>
	</div>

	<div class="actions">
		<s:submit key="lostPassword.submit" />
	</div>
</s:form>
