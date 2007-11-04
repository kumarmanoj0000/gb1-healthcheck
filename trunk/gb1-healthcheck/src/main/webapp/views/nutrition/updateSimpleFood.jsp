<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
	<body>
		<h2><fmt:message key="nutrition.foods.simpleFoods.update.title" /></h2>
		<s:form action="updateSubmit.go" method="post" namespace="/nutrition/simpleFood">
			<s:hidden name="foodId" />

			<div><s:textfield key="food.name" name="model.name" /></div>
			<div><s:select key="food.foodGroup" name="model.foodGroup" list="availableGroups" /></div>

			<div id="nutrientsList">
				<s:checkboxlist key="food.nutrients" name="model.selectedNutrients" list="availableNutrients" listKey="name()" listValue="name()" />
			</div>

			<div class="actions">
				<s:submit cssClass="button" key="nutrition.foods.simpleFoods.update.submit" />
				<s:submit cssClass="button" key="nutrition.foods.simpleFoods.update.cancel" name="method:cancel" />
			</div>
		</s:form>
	</body>
</html>
