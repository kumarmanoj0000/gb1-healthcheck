<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<%@ include file="/views/fragments/actionMessages.jsp"%>
<h2><s:text name="foods.title" /></h2>

<h3><s:text name="foods.simpleFoods" /></h3>

<s:url id="manageFoodsUrl" namespace="/foods" action="manageFoods" />
<s:url id="editSimpleFoodUrl" namespace="/foods" action="updateSimpleFood" method="input" />
<s:url id="editComplexFoodUrl" namespace="/foods" action="updateComplexFood" method="input" />

<s:form namespace="/foods" action="deleteSimpleFoods">
	<display:table name="simpleFoods" id="food"
			requestURI="${manageFoodsUrl}" excludedParams="*"
			pagesize="${foodListPageSize}" class="listTable"
			sort="list" defaultsort="2">
		<display:column style="width: 4%; text-align: center">
			<%-- bug WW-2339 prevents us from using the s:checkbox tag --%>
			<input type="checkbox" name="foodIds" value="${food.id}" />
		</display:column>
		<display:column titleKey="food.name" property="name" sortable="true" href="${editSimpleFoodUrl}" paramId="foodId" paramProperty="id" />
	</display:table>

	<p>
		<a href='<s:url namespace="/foods" action="createSimpleFood" method="input" />'><s:text name="foods.simpleFoods.create" /></a>
		<s:if test="simpleFoods.size() > 0">
			<s:submit cssClass="button" key="general.delete" />
		</s:if>
	</p>
</s:form>

<h3><s:text name="foods.complexFoods" /></h3>

<s:form namespace="/foods" action="deleteComplexFoods">
	<display:table name="complexFoods" id="food"
			requestURI="${manageFoodsUrl}" excludedParams="*"
			pagesize="${foodListPageSize}" class="listTable"
			sort="list" defaultsort="2">
		<display:column style="width: 4%; text-align: center">
			<%-- bug WW-2339 prevents us from using the s:checkbox tag --%>
			<input type="checkbox" name="foodIds" value="${food.id}" />
		</display:column>
		<display:column titleKey="food.name" property="name" sortable="true" href="${editComplexFoodUrl}" paramId="foodId" paramProperty="id" />
	</display:table>

	<p>
		<a href='<s:url namespace="/foods" action="createComplexFood" method="input" />'><s:text name="foods.complexFoods.create" /></a>
		<s:if test="complexFoods.size() > 0">
			<s:submit cssClass="button" key="general.delete" />
		</s:if>
	</p>
</s:form>
