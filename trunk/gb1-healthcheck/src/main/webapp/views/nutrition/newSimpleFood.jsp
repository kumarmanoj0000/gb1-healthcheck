<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
	<body>
		<h1><fmt:message key="nutrition.foods.simpleFoods.create.title" /></h1>
		<s:form action="createSubmit.go" method="post" namespace="/nutrition/simpleFood">
			<s:textfield key="food.name" name="model.name" /><br/>
			<s:select key="food.foodGroup" name="model.foodGroup" list="availableGroups" />
			<s:checkboxlist key="food.nutrients" name="model.selectedNutrients" list="availableNutrients" listKey="name()" listValue="name()" />

			<s:submit key="nutrition.foods.simpleFoods.create.submit" />
			<s:submit key="nutrition.foods.simpleFoods.create.cancel" name="method:cancel" />
		</s:form>
	</body>
</html>
