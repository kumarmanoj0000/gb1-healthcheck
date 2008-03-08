<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
	<body>
		<h2><fmt:message key="foods.simpleFoods.update.title" /></h2>

		<s:actionerror />

		<s:form action="updateSubmit.go" method="post" namespace="/foods/simpleFood">
			<s:hidden name="foodId" />

			<div class="required">
				<label><fmt:message key="food.name" />:</label>
				<s:textfield name="model.name" />
				<s:fielderror><s:param>model.name</s:param></s:fielderror>
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
				<s:submit cssClass="button" key="foods.simpleFoods.update.submit" />
				<s:submit cssClass="button" key="foods.simpleFoods.update.cancel" name="method:cancel" />
			</div>
		</s:form>
	</body>
</html>
