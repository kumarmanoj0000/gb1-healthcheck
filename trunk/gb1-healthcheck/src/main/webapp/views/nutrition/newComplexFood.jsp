<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
	<body>
		<h2><fmt:message key="nutrition.foods.complexFoods.create.title" /></h2>
		<s:form action="createSubmit.go" method="post" namespace="/nutrition/complexFood">
			<div class="required">
				<label><fmt:message key="food.name" />:</label>
				<s:textfield name="model.name" />
			</div>

			<fieldset id="ingredientsList">
				<legend><fmt:message key="food.ingredients" />:</legend>
				<s:checkboxlist name="model.selectedIngredientIds" list="availableIngredients" listKey="id" listValue="name" />
			</fieldset>

			<div class="actions">
				<s:submit cssClass="button" key="nutrition.foods.complexFoods.create.submit" />
				<s:submit cssClass="button" key="nutrition.foods.complexFoods.create.cancel" name="method:cancel" />
			</div>
		</s:form>
	</body>
</html>
