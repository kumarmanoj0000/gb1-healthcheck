<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
	<body>
		<h2><fmt:message key="foods.title" /></h2>

		<s:actionerror />

		<h3><fmt:message key="foods.simpleFoods" /></h3>
		<s:if test="simpleFoods.size == 0"><fmt:message key="foods.noSimpleFoods" /></s:if>
		<s:else>
			<ul>
				<fmt:message key="foods.simpleFoods.confirmDelete" var="deleteConfirmMsg" />
				<s:iterator value="simpleFoods">
					<li>
						<a href='<c:url value="/foods/simpleFood/updateInput.go?foodId=${id}" />'>${name}</a> |
						<a href='<c:url value="/foods/simpleFood/delete.go?foodId=${id}" />' onclick="return confirm('${deleteConfirmMsg}')">
							<fmt:message key="foods.simpleFoods.delete" />
						</a>
					</li>
				</s:iterator>
			</ul>
		</s:else>
		<a href='<c:url value="/foods/simpleFood/createInput.go" />'><fmt:message key="foods.simpleFoods.create" /></a>

		<h3><fmt:message key="foods.complexFoods" /></h3>
		<s:if test="complexFoods.size == 0"><fmt:message key="foods.noComplexFoods" /></s:if>
		<s:else>
			<ul>
				<fmt:message key="foods.complexFoods.confirmDelete" var="deleteConfirmMsg" />
				<s:iterator value="complexFoods">
					<li>
						<a href='<c:url value="/foods/complexFood/updateInput.go?foodId=${id}" />'>${name}</a> |
						<a href='<c:url value="/foods/complexFood/delete.go?foodId=${id}" />' onclick="return confirm('${deleteConfirmMsg}')">
							<fmt:message key="foods.complexFoods.delete" />
						</a>
					</li>
				</s:iterator>
			</ul>
		</s:else>
		<a href='<c:url value="/foods/complexFood/createInput.go" />'><fmt:message key="foods.complexFoods.create" /></a>
	</body>
</html>
