<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<%@ include file="/views/fragments/actionMessages.jsp"%>

<h2><s:text name="meals.title" /></h2>

<s:actionerror />

<s:if test="mealHistory.size == 0">
	<div><s:text name="meals.noMealHistory" /></div>
</s:if>
<s:else>
	<s:text name="meals.confirmDelete" id="deleteConfirmMsg" />
	<ul>
		<s:iterator value="mealHistory">
			<li>
				<s:url id="updateUrl" namespace="/meals" action="updateInput">
					<s:param name="mealId" value="%{id}" />
				</s:url>
				<a href="${updateUrl}">
					<fmt:formatDate value="${instant}" type="both" pattern="yyyy-MM-dd HH:mm" />
				</a>
				|
				<s:url id="deleteUrl" namespace="/meals" action="delete">
					<s:param name="mealId" value="%{id}" />
				</s:url>
				<a href="${deleteUrl}" onclick="return confirm('${deleteConfirmMsg}')">
					<s:text name="general.delete" />
				</a>
			</li>
		</s:iterator>
	</ul>
</s:else>

<p>
	<a href='<s:url namespace="/meals" action="createInput" />'><s:text name="meals.create" /></a>
</p>
