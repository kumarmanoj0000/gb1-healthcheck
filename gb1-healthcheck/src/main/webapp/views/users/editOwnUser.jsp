<%@ taglib prefix="s" uri="/struts-tags" %>

<h2><s:text name="users.edit.title" /></h2>
<s:actionerror />

<s:form namespace="/users" action="editOwnUser">
	<s:hidden name="model.userId" />

	<div class="required">
		<label><s:text name="users.edit.email" />:</label>
		<s:textfield name="model.email" />
		<s:fielderror><s:param>model.email</s:param></s:fielderror>
	</div>

	<div>
		<a href='<s:url namespace="/users" action="changePassword" method="input" />'><s:text name="users.changePassword" /></a>
	</div>

	<div class="actions">
		<s:submit cssClass="button" key="general.submit" />
		<s:submit cssClass="button" key="general.cancel" name="method:cancel" />
	</div>
</s:form>
