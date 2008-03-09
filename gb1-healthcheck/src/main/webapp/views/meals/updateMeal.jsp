<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
	<head>
		<%@ include file="/views/fragments/calendar.jsp" %>
		<script type="text/javascript" src='<c:url value="/scripts/jquery/jquery.js" />'></script>

		<script type="text/javascript">
			var nbSingleDishDivs = <s:property value="model.selectedFoodIds.length" />;
			var maxSingleDishDivIndex = nbSingleDishDivs;

			function addSingleDishDiv() {
				maxSingleDishDivIndex++;
				nbSingleDishDivs++;

				var newSingleDishDiv = $('#dishes div:first').clone()
					.attr('id', 'singleDish-' + maxSingleDishDivIndex).get();
				$('a:first', newSingleDishDiv).attr('onClick', 'javascript:removeSingleDishDiv(' + maxSingleDishDivIndex + ')');
				$(newSingleDishDiv).insertBefore('#addSingleDishLinkDiv');
			}

			function removeSingleDishDiv(index) {
				if (nbSingleDishDivs > 1) {
					$('#singleDish-' + index).remove();
					nbSingleDishDivs--;
				}
			}
		</script>
	</head>

	<body>
		<h2><fmt:message key="meals.update.title" /></h2>

		<s:actionerror />

		<s:form namespace="/meals" action="updateSubmit">
			<s:hidden name="mealId" />
			<s:hidden name="eaterId" />

			<div class="required">
				<label><fmt:message key="meal.instant" />:</label>
				<s:date id="instant" name="model.instant" format="yyyy-MM-dd hh:mm:ss" />
				<s:textfield id="f_date_c" key="meal.instant" name="model.instant" readonly="1" />
				<img
					id="f_trigger_c"
					src="<c:url value='/scripts/jscalendar/img.gif' />"
					style="cursor: pointer; border: 1px solid red;"
					title="Date selector"
					onmouseover="this.style.background='red';"
					onmouseout="this.style.background=''"
				/>
			</div>

			<fieldset id="dishes">
				<legend><fmt:message key="meals.create.dishes" /></legend>

				<s:iterator value="model.selectedFoodIds" status="it">
					<div id="singleDish-${it.index}">
						<div class="required">
							<label><fmt:message key="meal.dish" />:</label>
							<s:select
								name="model.selectedFoodIds"
								value="model.selectedFoodIds[#it.index]"
								list="availableFoods"
								listKey="id"
								listValue="name"
							/>
						</div>

						<div class="required">
							<label><fmt:message key="meal.preparationMethod" />:</label>
							<s:select
								name="model.selectedPreparationMethodNames"
								value="model.selectedPreparationMethodNames[#it.index]"
								list="availablePreparationMethods"
								listKey="name()"
								listValue="name()"
							/>
						</div>

						<div>
							<a href="#" onClick="javascript:removeSingleDishDiv(${it.index})"><fmt:message key="meals.update.removeDish" /></a>
						</div>
					</div>
				</s:iterator>

				<div id="addSingleDishLinkDiv">
					<a href="#" onClick="javascript:addSingleDishDiv()"><fmt:message key="meals.update.addDish" /></a>
				</div>
			</fieldset>

			<div class="actions">
				<s:submit cssClass="button" key="meals.update.submit" />
				<s:submit cssClass="button" key="meals.update.cancel" name="method:cancel" />
			</div>
		</s:form>

		<script type="text/javascript">
			Calendar.setup({
				inputField  : "f_date_c",
				ifFormat    : "%Y-%m-%d %H:%M:%S",
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
