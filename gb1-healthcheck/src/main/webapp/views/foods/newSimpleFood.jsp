<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
	<body>
		<h2><fmt:message key="foods.simpleFoods.create.title" /></h2>
		<s:form action="createSubmit.go" method="post" namespace="/foods/simpleFood">
			<div class="required">
				<label><fmt:message key="food.name" />:</label>
				<s:textfield name="model.name" />
			</div>

			<div class="required">
				<label><fmt:message key="food.foodGroup" />:</label>
				<s:select name="model.foodGroup" list="availableGroups" />
			</div>

			<fieldset id="nutrientsList">
				<legend><fmt:message key="food.nutrients" />:</legend>
				<s:checkboxlist name="model.selectedNutrients" list="availableNutrients" listKey="name()" listValue="name()" />
			</fieldset>

			<div class="actions">
				<s:submit cssClass="button" key="foods.simpleFoods.create.submit" />
				<s:submit cssClass="button" key="foods.simpleFoods.create.cancel" name="method:cancel" />
			</div>
		</s:form>
	</body>
</html>
