<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
	<body>
		<h1><fmt:message key="nutrition.foods.complexFoods.update.title" /></h1>
		<s:form action="updateSubmit.go" method="post" namespace="/nutrition/complexFood">
			<s:hidden name="foodId" />
			<s:textfield key="food.name" name="model.name" /><br/>
			<s:checkboxlist key="food.ingredients" name="model.selectedIngredientIds" list="availableIngredients" listKey="id" listValue="name" />

			<s:submit key="nutrition.foods.complexFoods.update.submit" />
			<s:submit key="nutrition.foods.complexFoods.update.cancel" name="method:cancel" />
		</s:form>
	</body>
</html>
