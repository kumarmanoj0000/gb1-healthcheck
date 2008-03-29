<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<%@ include file="/views/fragments/actionMessages.jsp"%>
<h2><s:text name="users.list.title" /></h2>

<s:set name="userListPageSize" value="@com.gb1.healthcheck.web.WebConstants@DEFAULT_PAGE_SIZE" />
<s:url id="listUsersUrl" namespace="/users" action="listUsers" />

<display:table
		name="users" pagesize="${userListPageSize}" requestURI="${listUsersUrl}" class="listTable"
		sort="list" defaultsort="1">
	<display:column property="login" sortable="true" />
	<display:column property="email" sortable="true" />
</display:table>
