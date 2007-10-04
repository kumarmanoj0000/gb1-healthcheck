<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
	<body>
		<h1><fmt:message key="nutrition.foods.simpleFoods.update.title" /></h1>
		<s:form action="updateSubmit.go" method="post" namespace="/nutrition/simpleFood">
			<s:hidden name="foodId" />
			<s:textfield key="food.name" name="model.name" /><br/>
			<s:select key="food.foodGroup" name="model.foodGroup" list="availableGroups" />
			<s:checkboxlist key="food.nutrients" name="model.selectedNutrients" list="availableNutrients" listKey="name()" listValue="name()" />

			<s:submit key="nutrition.foods.simpleFoods.update.submit" />
			<s:submit key="nutrition.foods.simpleFoods.update.cancel" name="method:cancel" />
		</s:form>
	</body>
</html>
