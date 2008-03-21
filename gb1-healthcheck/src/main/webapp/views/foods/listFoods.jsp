<%@ taglib prefix="s" uri="/struts-tags" %>

<%@ include file="/views/fragments/actionMessages.jsp"%>
<h2><s:text name="foods.title" /></h2>

<h3><s:text name="foods.simpleFoods" /></h3>

<s:if test="simpleFoods.size == 0">
	<div><s:text name="foods.noSimpleFoods" /></div>
</s:if>
<s:else>
	<ul>
		<s:text name="foods.simpleFoods.confirmDelete" id="deleteConfirmMsg" />
		<s:iterator value="simpleFoods">
			<li>
				<s:url id="updateUrl" namespace="/foods" action="updateSimpleFood" method="input">
					<s:param name="foodId" value="%{id}" />
				</s:url>
				<s:url id="deleteUrl" namespace="/foods" action="deleteSimpleFood">
					<s:param name="foodId" value="%{id}" />
				</s:url>
				<a href="${updateUrl}">${name}</a> |
				<a href="${deleteUrl}" onclick="return confirm('${deleteConfirmMsg}')"><s:text name="general.delete" /></a>
			</li>
		</s:iterator>
	</ul>
</s:else>

<p>
	<a href='<s:url namespace="/foods" action="createSimpleFood" method="input" />'><s:text name="foods.simpleFoods.create" /></a>
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
				<s:url id="updateUrl" namespace="/foods" action="updateComplexFood" method="input">
					<s:param name="foodId" value="%{id}" />
				</s:url>
				<s:url id="deleteUrl" namespace="/foods" action="deleteComplexFood">
					<s:param name="foodId" value="%{id}" />
				</s:url>
				<a href="${updateUrl}">${name}</a> |
				<a href="${deleteUrl}" onclick="return confirm('${deleteConfirmMsg}')"><s:text name="general.delete" /></a>
			</li>
		</s:iterator>
	</ul>
</s:else>

<p>
	<a href='<s:url namespace="/foods" action="createComplexFood" method="input" />'><s:text name="foods.complexFoods.create" /></a>
</p>
