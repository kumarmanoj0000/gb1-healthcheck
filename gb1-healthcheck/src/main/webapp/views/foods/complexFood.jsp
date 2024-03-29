<%@ taglib prefix="s" uri="/struts-tags" %>

<h2><s:text name="foods.complexFoods.edit.title" /></h2>

<s:actionerror />

<s:form namespace="/foods" action="saveComplexFood">
	<div class="required">
		<label><s:text name="food.name" />:</label>
		<s:textfield name="model.name" />
		<s:fielderror><s:param>model.name</s:param></s:fielderror>
	</div>

	<fieldset id="ingredientsList">
		<legend><s:text name="food.ingredients" />:</legend>
		<s:checkboxlist theme="gb1" name="model.ingredientIds" list="availableIngredients" listKey="id" listValue="name" />
	</fieldset>

	<div class="actions">
		<s:submit cssClass="button" key="general.submit" />
		<s:submit cssClass="button" key="general.cancel" name="method:cancel" />
	</div>
</s:form>
