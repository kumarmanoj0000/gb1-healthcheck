<%@ taglib prefix="s" uri="/struts-tags" %>

<h2><s:text name="users.edit.title" /></h2>

<s:actionerror />

<s:form namespace="/users" action="changePassword">
	<div class="required">
		<label><s:text name="users.changePassword.currentPassword" />:</label>
		<s:password name="currentPassword" />
		<s:fielderror><s:param>currentPassword</s:param></s:fielderror>
	</div>

	<div class="required">
		<label><s:text name="users.changePassword.newPassword1" />:</label>
		<s:password name="newPassword1" />
		<s:fielderror><s:param>newPassword1</s:param></s:fielderror>
	</div>

	<div class="required">
		<label><s:text name="users.changePassword.newPassword2" />:</label>
		<s:password name="newPassword2" />
		<s:fielderror><s:param>newPassword2</s:param></s:fielderror>
	</div>

	<div class="actions">
		<s:submit cssClass="button" key="general.submit" />
		<s:submit cssClass="button" key="general.cancel" name="method:cancel" />
	</div>
</s:form>
