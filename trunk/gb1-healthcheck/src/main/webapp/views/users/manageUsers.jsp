<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<%@ include file="/views/fragments/actionMessages.jsp"%>
<h2><s:text name="users.list.title" /></h2>

<s:url id="listUsersUrl" namespace="/users" action="manageUsers" />
<s:url id="editUserUrl" namespace="/users" action="editUser" method="input" />

<s:form namespace="/users" action="deleteUsers">
	<display:table name="users" id="user"
			requestURI="${listUsersUrl}" excludedParams="*"
			pagesize="${userListPageSize}" class="listTable"
			sort="list" defaultsort="2" decorator="com.gb1.healthcheck.web.users.UserListTableDecorator">
		<display:column style="width: 4%; text-align: center">
			<%-- bug WW-2339 prevents us from using the s:checkbox tag --%>
			<input type="checkbox" name="userIds" value="${user.id}" />
		</display:column>
		<display:column titleKey="user.login" property="login" sortable="true" href="${editUserUrl}" paramId="userId" paramProperty="id" />
		<display:column titleKey="user.email" property="email" sortable="true" />
		<display:column title="" property="resetPasswordLink" />
	</display:table>

	<s:if test="users.size() > 0">
		<p><s:submit cssClass="button" key="general.delete" /></p>
	</s:if>
</s:form>
