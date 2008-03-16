<%@ taglib prefix="s" uri="/struts-tags" %>

<div><s:text name="error.message" /></div>
<div><s:text name="error.adminContacted" /></div>

<p>
	<textarea rows="20" cols="90" readonly="readonly">${exceptionStack}</textarea>
</p>
