<%@ taglib prefix="s" uri="/struts-tags" %>

<%@ include file="/views/fragments/actionMessages.jsp"%>
<h2><s:text name="users.list.title" /></h2>

<s:if test="users.size == 0">
	<div><s:text name="users.list.noUsers" /></div>
</s:if>
<s:else>
	<s:url id="sortByLoginUrl" namespace="/users" action="listUsers" method="sort">
		<s:param name="sortBy" value="%{'login'}" />
		<s:param name="sortAscending" value="%{!sortAscending}" />
	</s:url>
	<s:url id="sortByEmailUrl" namespace="/users" action="listUsers" method="sort">
		<s:param name="sortBy" value="%{'email'}" />	
		<s:param name="sortAscending" value="%{!sortAscending}" />
	</s:url>

	<table>
		<tr>
			<th>&nbsp;</th>
			<th><a href="${sortByLoginUrl}"><s:text name="user.login" /></a></th>
			<th><a href="${sortByEmailUrl}"><s:text name="user.email" /></a></th>
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
