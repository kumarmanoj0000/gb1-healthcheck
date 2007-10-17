<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
	<body>
		<h1><fmt:message key="nutrition.meals.title" /></h1>

		<s:if test="mealHistory.size == 0"><fmt:message key="nutrition.meals.noMealHistory" /></s:if>
		<s:else>
			<ul>
				<s:iterator value="mealHistory">
					<li>
						<fmt:formatDate value="${dateAndTime}" type="both" pattern="yyyy-MM-dd HH:mm" />
					</li>
				</s:iterator>
			</ul>
		</s:else>
		<a href='<c:url value="/nutrition/meals/createInput.go" />'><fmt:message key="nutrition.meals.create" /></a>
	</body>
</html>
