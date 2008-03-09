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
						<s:url id="updateUrl" namespace="/foods/simpleFood" action="updateInput">
							<s:param name="foodId" value="%{id}" />
						</s:url>
						<s:url id="deleteUrl" namespace="/foods/simpleFood" action="delete">
							<s:param name="foodId" value="%{id}" />
						</s:url>
						<a href="${updateUrl}">${name}</a> |
						<a href="${deleteUrl}" onclick="return confirm('${deleteConfirmMsg}')"><fmt:message key="foods.simpleFoods.delete" /></a>
					</li>
				</s:iterator>
			</ul>
		</s:else>

		<a href='<s:url namespace="/foods/simpleFood" action="createInput" />'><fmt:message key="foods.simpleFoods.create" /></a>

		<h3><fmt:message key="foods.complexFoods" /></h3>
		<s:if test="complexFoods.size == 0"><fmt:message key="foods.noComplexFoods" /></s:if>
		<s:else>
			<ul>
				<fmt:message key="foods.complexFoods.confirmDelete" var="deleteConfirmMsg" />
				<s:iterator value="complexFoods">
					<li>
						<s:url id="updateUrl" namespace="/foods/complexFood" action="updateInput">
							<s:param name="foodId" value="%{id}" />
						</s:url>
						<s:url id="deleteUrl" namespace="/foods/complexFood" action="delete">
							<s:param name="foodId" value="%{id}" />
						</s:url>
						<a href="${updateUrl}">${name}</a> |
						<a href="${deleteUrl}" onclick="return confirm('${deleteConfirmMsg}')"><fmt:message key="foods.complexFoods.delete" /></a>
					</li>
				</s:iterator>
			</ul>
		</s:else>
		<a href='<s:url namespace="/foods/complexFood" action="createInput" />'><fmt:message key="foods.complexFoods.create" /></a>
	</body>
</html>
