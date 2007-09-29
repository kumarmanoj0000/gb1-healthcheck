<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
	<body>
		<h1><fmt:message key="nutrition.foods.simpleFoods.update.title" /></h1>
		<s:form action="updateSimpleFood.go" method="post" namespace="/nutrition">
			<s:hidden name="foodId" />
			<s:textfield key="food.name" name="model.name" /><br/>
			<s:select key="food.group" name="model.group" list="availableGroups" />
			<s:checkboxlist key="food.nutrients" name="model.selectedNutrients" list="availableNutrients" listKey="name()" listValue="name()" />
			<s:submit key="nutrition.foods.simpleFoods.update.submit" />
		</s:form>
	</body>
</html>
