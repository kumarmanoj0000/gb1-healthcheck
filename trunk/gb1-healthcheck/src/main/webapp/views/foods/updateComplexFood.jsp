<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
	<body>
		<h2><s:text name="foods.complexFoods.update.title" /></h2>

		<s:actionerror />

		<s:form namespace="/foods/complexFood" action="updateSubmit">
			<s:hidden name="foodId" />

			<div class="required">
				<label><s:text name="food.name" />:</label>
				<s:textfield name="model.name" />
				<s:fielderror><s:param>model.name</s:param></s:fielderror>
			</div>

			<fieldset id="ingredientsList">
				<legend><s:text name="food.ingredients" />:</legend>
				<s:checkboxlist theme="gb1" name="model.selectedIngredientIds" list="availableIngredients" listKey="id" listValue="name" />
			</fieldset>

			<div class="actions">
				<s:submit cssClass="button" key="general.submit" />
				<s:submit cssClass="button" key="general.cancel" name="method:cancel" />
			</div>
		</s:form>
	</body>
</html>
