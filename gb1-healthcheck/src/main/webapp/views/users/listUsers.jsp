<%@ taglib prefix="s" uri="/struts-tags" %>

<%@ include file="/views/fragments/actionMessages.jsp"%>
<h2><s:text name="users.list.title" /></h2>

<s:if test="users.size == 0">
	<div><s:text name="users.list.noUsers" /></div>
</s:if>
<s:else>
	<table>
		<tr>
			<th>&nbsp;</th>
			<th><s:text name="user.login" /></th>
			<th><s:text name="user.email" /></th>
		</tr>

		<s:iterator value="users">
			<tr>
				<td>&nbsp;</td>
				<td>${login}</td>
				<td>${email}</td>
			</tr>
		</s:iterator>
	</table>

</s:else>
