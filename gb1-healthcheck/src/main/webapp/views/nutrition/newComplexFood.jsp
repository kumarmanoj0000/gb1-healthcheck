<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
	<body>
		<h2><fmt:message key="nutrition.foods.complexFoods.create.title" /></h2>
		<s:form action="createSubmit.go" method="post" namespace="/nutrition/complexFood">
			<s:textfield key="food.name" name="model.name" /><br/>
			<s:checkboxlist key="food.ingredients" name="model.selectedIngredientIds" list="availableIngredients" listKey="id" listValue="name" />

			<s:submit key="nutrition.foods.complexFoods.create.submit" />
			<s:submit key="nutrition.foods.complexFoods.create.cancel" name="method:cancel" />
		</s:form>
	</body>
</html>
