<%@ taglib prefix="s" uri="/struts-tags" %>

<h2><s:text name="foods.simpleFoods.edit.title" /></h2>

<s:actionerror />

<s:form namespace="/foods" action="%{foodId == null ? 'createSimpleFood' : 'updateSimpleFood'}">
	<s:hidden name="foodId" />

	<div class="required">
		<label><s:text name="food.name" />:</label>
		<s:textfield name="model.name" />
		<s:fielderror><s:param>model.name</s:param></s:fielderror>
	</div>

	<div class="required">
		<label><s:text name="food.foodGroup" />:</label>
		<s:select name="model.foodGroup" list="availableGroups" listKey="name()" listValue="%{getText('foodGroup.' + name())}" />
	</div>

	<fieldset id="nutrientsList">
		<legend><s:text name="food.nutrients" />:</legend>
		<s:checkboxlist theme="gb1" name="model.selectedNutrients" list="availableNutrients" listKey="name()" listValue="%{getText('nutrient.' + name())}" />
	</fieldset>

	<div class="actions">
		<s:submit cssClass="button" key="general.submit" />
		<s:submit cssClass="button" key="general.cancel" name="method:cancel" />
	</div>
</s:form>
