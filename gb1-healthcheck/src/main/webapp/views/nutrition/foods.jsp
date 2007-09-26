<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
	<body>
		<h1><fmt:message key="nutrition.foods.title" /></h1>

		<h2><fmt:message key="nutrition.foods.simpleFoods" /></h2>
		<s:if test="simpleFoods.size == 0"><fmt:message key="nutrition.foods.noSimpleFoods" /></s:if>
		<s:else>
			<ul>
				<s:iterator value="simpleFoods">
					<li>
						${name} |
						<a href='<c:url value="/nutrition/prepareSimpleFoodUpdate.go?foodId=${id}" />'><fmt:message key="nutrition.foods.simpleFoods.edit" /></a> |
						<fmt:message key="nutrition.foods.simpleFoods.delete" />
					</li>
				</s:iterator>
			</ul>
		</s:else>
		<a href='<c:url value="/nutrition/prepareNewSimpleFood.go" />'><fmt:message key="nutrition.foods.simpleFoods.create" /></a>

		<h2><fmt:message key="nutrition.foods.complexFoods" /></h2>
		<s:if test="complexFoods.size == 0"><fmt:message key="nutrition.foods.noComplexFoods" /></s:if>
		<s:else>
			<ul>
				<s:iterator value="complexFoods">
					<li>${name}</li>
				</s:iterator>
			</ul>
		</s:else>
	</body>
</html>
