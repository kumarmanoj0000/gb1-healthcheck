<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<%@ include file="/views/fragments/actionMessages.jsp"%>
<h2><s:text name="users.list.title" /></h2>

<s:url id="listUsersUrl" namespace="/users" action="listUsers" />

<s:form namespace="/users" action="deleteUsers">
	<display:table name="users" id="user"
			requestURI="${listUsersUrl}" excludedParams="*"
			pagesize="${userListPageSize}" class="listTable"
			sort="list" defaultsort="2">
		<display:column style="width: 4%; text-align: center">
			<%-- bug WW-2339 prevents us from using the s:checkbox tag --%>
			<input type="checkbox" name="userIds" value="${user.id}" />
		</display:column>
		<display:column property="login" sortable="true" />
		<display:column property="email" sortable="true" />
	</display:table>

	<s:submit cssClass="button" key="general.delete" />
</s:form>
