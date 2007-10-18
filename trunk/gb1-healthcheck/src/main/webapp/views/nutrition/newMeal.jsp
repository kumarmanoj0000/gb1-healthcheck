<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
	<head>
		<link rel="stylesheet" type="text/css" href="<c:url value='/scripts/jscalendar/calendar-win2k-1.css' />" title="win2k-1"></link>
		<script type="text/javascript" src="<c:url value='/scripts/jscalendar/calendar.js' />"></script>
		<script type="text/javascript" src="<c:url value='/scripts/jscalendar/calendar-setup.js' />"></script>
		<!-- TODO Load the calendar resource file based on locale -->
		<script type="text/javascript" src="<c:url value='/scripts/jscalendar/lang/calendar-en.js' />"></script>

		<script type="text/javascript">
			var nbSingleDishDivs = 1;
			var maxSingleDishDivIndex = 0;

			function addSingleDishDiv() {
				var dishesDiv = document.getElementById('dishes');
				var singleDishDiv = dishesDiv.getElementsByTagName('div')[0];

				maxSingleDishDivIndex++;
				var newSingleDishDiv = singleDishDiv.cloneNode(true);
				newSingleDishDiv.id = 'dishes.singleDish-' + maxSingleDishDivIndex;

				var newSingleDishDivRemoveLink = newSingleDishDiv.getElementsByTagName('a')[0];
				newSingleDishDivRemoveLink.setAttribute('onClick', 'javascript:removeSingleDishDiv(' + maxSingleDishDivIndex + ')');

				dishesDiv.appendChild(newSingleDishDiv);
				nbSingleDishDivs++;
			}

			function removeSingleDishDiv(index) {
				if (nbSingleDishDivs > 1) {
					var dishesDiv = document.getElementById('dishes');
					var singleDishDiv = document.getElementById('dishes.singleDish-' + index);

					if (singleDishDiv) {
						dishesDiv.removeChild(singleDishDiv);
						nbSingleDishDivs--;
					}
				}
			}
		</script>
	</head>

	<body>
		<h1><fmt:message key="nutrition.meals.create.title" /></h1>
		<s:form action="createSubmit.go" method="post" namespace="/nutrition/meals">
			<s:textfield key="meal.dateAndTime" name="model.dateAndTime" id="f_date_c" readonly="1" />
			<img
				id="f_trigger_c"
				src="<c:url value='/scripts/jscalendar/img.gif' />"
				style="cursor: pointer; border: 1px solid red;"
				title="Date selector"
				onmouseover="this.style.background='red';"
				onmouseout="this.style.background=''"
			/>

			<div id="dishes">
				<div id="dishes.singleDish-0">
					<s:select
						key="meal.dish"
						name="model.selectedFoodIds"
						list="availableFoods"
						listKey="id"
						listValue="name"
					/>
					<s:select
						key="meal.preparationMethod"
						name="model.selectedPreparationMethodNames"
						list="availablePreparationMethods"
						listKey="name()"
						listValue="name()"
					/>

					<a href="#" onClick="javascript:removeSingleDishDiv(0)"><fmt:message key="nutrition.meals.create.removeDish" /></a>
				</div>
			</div>

			<a href="#" onClick="javascript:addSingleDishDiv()"><fmt:message key="nutrition.meals.create.addDish" /></a>

			<s:submit key="nutrition.meals.create.submit" />
			<s:submit key="nutrition.meals.create.cancel" name="method:cancel" />
		</s:form>

		<script type="text/javascript">
			Calendar.setup({
				inputField  : "f_date_c",
				ifFormat    : "%Y/%m/%d %H:%M",
				button      : "f_trigger_c",
				align       : "Tl",
				singleClick : false,
				showsTime   : true,
				timeFormat  : 24,
				step        : 1
			});
		</script>
	</body>
</html>
