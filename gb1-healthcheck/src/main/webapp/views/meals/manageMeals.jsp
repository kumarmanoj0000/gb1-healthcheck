<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<%@ include file="/views/fragments/actionMessages.jsp"%>
<h2><s:text name="meals.title" /></h2>

<s:actionerror />

<s:url id="manageMealsUrl" namespace="/meals" action="manageMeals" />
<s:url id="editMealUrl" namespace="/meals" action="saveMeal" method="input" />

<s:form namespace="/meals" action="deleteMeals">
	<display:table name="meals" id="meal"
			requestURI="${manageMealsUrl}" excludedParams="*"
			pagesize="${mealListPageSize}" class="listTable"
			sort="list" defaultsort="2">
		<display:column style="width: 4%; text-align: center">
			<%-- bug WW-2339 prevents us from using the s:checkbox tag --%>
			<input type="checkbox" name="mealIds" value="${meal.id}" />
		</display:column>
		<display:column titleKey="meal.instant" property="instant" format="{0,date,yyyy/MM/dd HH:mm}" sortable="true"
			href="${editMealUrl}" paramId="mealId" paramProperty="id"
		/>
	</display:table>

	<p>
		<a href='<s:url namespace="/meals" action="saveMeal" method="input" />'><s:text name="meals.create" /></a>
		<s:if test="meals.size() > 0">
			<s:submit cssClass="button" key="general.delete" />
		</s:if>
	</p>
</s:form>
