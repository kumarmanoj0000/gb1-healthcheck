<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
	<body>
		<h2><fmt:message key="meals.title" /></h2>

		<s:actionerror />

		<div>
			<s:if test="mealHistory.size == 0">
				<fmt:message key="meals.noMealHistory" />
			</s:if>
			<s:else>
				<fmt:message key="meals.confirmDelete" var="deleteConfirmMsg" />
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
								<fmt:message key="meals.delete" />
							</a>
						</li>
					</s:iterator>
				</ul>
			</s:else>
		</div>

		<div>
			<a href='<s:url namespace="/meals" action="createInput" />'><fmt:message key="meals.create" /></a>
		</div>
	</body>
</html>
