<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
	<body>
		<h2><fmt:message key="nutrition.meals.title" /></h2>

		<s:if test="mealHistory.size == 0"><fmt:message key="nutrition.meals.noMealHistory" /></s:if>
		<s:else>
			<fmt:message key="nutrition.meals.confirmDelete" var="deleteConfirmMsg" />
			<ul>
				<s:iterator value="mealHistory">
					<li>
						<a href='<c:url value="/nutrition/meals/updateInput.go?mealId=${id}" />'>
							<fmt:formatDate value="${instant}" type="both" pattern="yyyy-MM-dd HH:mm" />
						</a>
						|
						<a href='<c:url value="/nutrition/meals/delete.go?mealId=${id}" />' onclick="return confirm('${deleteConfirmMsg}')">
							<fmt:message key="nutrition.meals.delete" />
						</a>
					</li>
				</s:iterator>
			</ul>
		</s:else>
		<a href='<c:url value="/nutrition/meals/createInput.go" />'><fmt:message key="nutrition.meals.create" /></a>
	</body>
</html>
