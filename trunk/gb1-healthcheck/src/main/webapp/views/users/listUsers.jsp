<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<%@ include file="/views/fragments/actionMessages.jsp"%>
<h2><s:text name="users.list.title" /></h2>

<s:url id="listUsersUrl" namespace="/users" action="listUsers" />

<display:table name="users" pagesize="10" requestURI="${listUsersUrl}" class="listTable" defaultsort="1">
	<display:column property="login" sortable="true" />
	<display:column property="email" sortable="true" />
</display:table>
