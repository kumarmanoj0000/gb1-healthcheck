<%@ taglib prefix="s" uri="/struts-tags"%>

<s:actionerror />

<s:form namespace="/public/register" action="userRegistration">
	<div class="required">
		<label><s:text name="register.login" />:</label>
		<s:textfield name="model.login" />
		<s:fielderror><s:param>model.login</s:param></s:fielderror>
	</div>

	<div class="required">
		<label><s:text name="register.email" />:</label>
		<s:textfield name="model.email" />
		<s:fielderror><s:param>model.email</s:param></s:fielderror>
	</div>

	<div class="required">
		<label><s:text name="register.password1" />:</label>
		<s:textfield name="model.password1" />
		<s:fielderror><s:param>model.password1</s:param></s:fielderror>
	</div>

	<div class="required">
		<label><s:text name="register.password2" />:</label>
		<s:textfield name="model.password2" />
		<s:fielderror><s:param>model.password2</s:param></s:fielderror>
	</div>

	<div class="actions">
		<s:submit key="register.submit" />
	</div>
</s:form>
