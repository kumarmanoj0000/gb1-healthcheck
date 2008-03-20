<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
	<body>
		<%@ include file="/views/fragments/actionMessages.jsp"%>

		<h2><s:text name="foods.title" /></h2>

		<s:actionerror />

		<h3><s:text name="foods.simpleFoods" /></h3>

		<s:if test="simpleFoods.size == 0">
			<div><s:text name="foods.noSimpleFoods" /></div>
		</s:if>
		<s:else>
			<ul>
				<s:text name="foods.simpleFoods.confirmDelete" id="deleteConfirmMsg" />
				<s:iterator value="simpleFoods">
					<li>
						<s:url id="updateUrl" namespace="/foods/simpleFood" action="updateInput">
							<s:param name="foodId" value="%{id}" />
						</s:url>
						<s:url id="deleteUrl" namespace="/foods/simpleFood" action="delete">
							<s:param name="foodId" value="%{id}" />
						</s:url>
						<a href="${updateUrl}">${name}</a> |
						<a href="${deleteUrl}" onclick="return confirm('${deleteConfirmMsg}')"><s:text name="general.delete" /></a>
					</li>
				</s:iterator>
			</ul>
		</s:else>

		<p>
			<a href='<s:url namespace="/foods/simpleFood" action="createInput" />'><s:text name="foods.simpleFoods.create" /></a>
		</p>

		<h3><s:text name="foods.complexFoods" /></h3>

		<s:if test="complexFoods.size == 0">
			<div><s:text name="foods.noComplexFoods" /></div>
		</s:if>
		<s:else>
			<ul>
				<s:text name="foods.complexFoods.confirmDelete" id="deleteConfirmMsg" />
				<s:iterator value="complexFoods">
					<li>
						<s:url id="updateUrl" namespace="/foods/complexFood" action="updateInput">
							<s:param name="foodId" value="%{id}" />
						</s:url>
						<s:url id="deleteUrl" namespace="/foods/complexFood" action="delete">
							<s:param name="foodId" value="%{id}" />
						</s:url>
						<a href="${updateUrl}">${name}</a> |
						<a href="${deleteUrl}" onclick="return confirm('${deleteConfirmMsg}')"><s:text name="general.delete" /></a>
					</li>
				</s:iterator>
			</ul>
		</s:else>

		<p>
			<a href='<s:url namespace="/foods/complexFood" action="createInput" />'><s:text name="foods.complexFoods.create" /></a>
		</p>
	</body>
</html>
