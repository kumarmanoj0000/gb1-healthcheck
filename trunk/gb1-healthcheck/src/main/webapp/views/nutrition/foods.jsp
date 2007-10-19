<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
	<body>
		<h3><fmt:message key="nutrition.foods.title" /></h3>

		<h3><fmt:message key="nutrition.foods.simpleFoods" /></h3>
		<s:if test="simpleFoods.size == 0"><fmt:message key="nutrition.foods.noSimpleFoods" /></s:if>
		<s:else>
			<ul>
				<fmt:message key="nutrition.foods.simpleFoods.confirmDelete" var="deleteConfirmMsg" />
				<s:iterator value="simpleFoods">
					<li>
						<a href='<c:url value="/nutrition/simpleFood/updateInput.go?foodId=${id}" />'>${name}</a> |
						<a href='<c:url value="/nutrition/simpleFood/delete.go?foodId=${id}" />' onclick="return confirm('${deleteConfirmMsg}')">
							<fmt:message key="nutrition.foods.simpleFoods.delete" />
						</a>
					</li>
				</s:iterator>
			</ul>
		</s:else>
		<a href='<c:url value="/nutrition/simpleFood/createInput.go" />'><fmt:message key="nutrition.foods.simpleFoods.create" /></a>

		<h3><fmt:message key="nutrition.foods.complexFoods" /></h3>
		<s:if test="complexFoods.size == 0"><fmt:message key="nutrition.foods.noComplexFoods" /></s:if>
		<s:else>
			<ul>
				<fmt:message key="nutrition.foods.complexFoods.confirmDelete" var="deleteConfirmMsg" />
				<s:iterator value="complexFoods">
					<li>
						<a href='<c:url value="/nutrition/complexFood/updateInput.go?foodId=${id}" />'>${name}</a> |
						<a href='<c:url value="/nutrition/complexFood/delete.go?foodId=${id}" />' onclick="return confirm('${deleteConfirmMsg}')">
							<fmt:message key="nutrition.foods.complexFoods.delete" />
						</a>
					</li>
				</s:iterator>
			</ul>
		</s:else>
		<a href='<c:url value="/nutrition/complexFood/createInput.go" />'><fmt:message key="nutrition.foods.complexFoods.create" /></a>
	</body>
</html>
