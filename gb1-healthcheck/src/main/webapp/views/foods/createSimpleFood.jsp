<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
	<body>
		<h2><s:text name="foods.simpleFoods.create.title" /></h2>

		<s:actionerror />

		<s:form namespace="/foods/simpleFood" action="createSubmit">
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
				<s:checkboxlist name="model.selectedNutrients" list="availableNutrients" listKey="name()" listValue="%{getText('nutrient.' + name())}" />
			</fieldset>

			<div class="actions">
				<s:submit cssClass="button" key="foods.simpleFoods.create.submit" />
				<s:submit cssClass="button" key="foods.simpleFoods.create.cancel" name="method:cancel" />
			</div>
		</s:form>
	</body>
</html>
