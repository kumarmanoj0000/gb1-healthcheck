<%@ taglib prefix="s" uri="/struts-tags" %>

<s:if test="hasActionMessages()">
	<div class="actionMessages"><s:actionmessage /></div>
</s:if>
