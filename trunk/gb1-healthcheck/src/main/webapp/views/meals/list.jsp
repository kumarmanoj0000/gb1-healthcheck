<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
	<body>
		<h2><fmt:message key="meals.title" /></h2>

		<div>
			<s:if test="mealHistory.size == 0">
				<fmt:message key="meals.noMealHistory" />
			</s:if>
			<s:else>
				<fmt:message key="meals.confirmDelete" var="deleteConfirmMsg" />
				<ul>
					<s:iterator value="mealHistory">
						<li>
							<a href='<c:url value="/meals/updateInput.go?mealId=${id}" />'>
								<fmt:formatDate value="${instant}" type="both" pattern="yyyy-MM-dd HH:mm" />
							</a>
							|
							<a href='<c:url value="/meals/delete.go?mealId=${id}" />' onclick="return confirm('${deleteConfirmMsg}')">
								<fmt:message key="meals.delete" />
							</a>
						</li>
					</s:iterator>
				</ul>
			</s:else>
		</div>

		<div>
			<a href='<c:url value="/meals/createInput.go" />'><fmt:message key="meals.create" /></a>
		</div>
	</body>
</html>
