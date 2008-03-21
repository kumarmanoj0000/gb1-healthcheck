<%@ taglib prefix="s" uri="/struts-tags"%>

<s:actionerror />

<s:form namespace="/public/register" action="activate">
	<div class="required">
		<label><s:text name="register.activate.email" />:</label>
		<s:textfield name="principal" />
		<s:fielderror><s:param>principal</s:param></s:fielderror>
	</div>

	<div class="required">
		<label><s:text name="register.activate.activationToken" />:</label>
		<s:textfield name="credentials" />
		<s:fielderror><s:param>credentials</s:param></s:fielderror>
	</div>

	<s:submit key="register.activate.submit" />
</s:form>
