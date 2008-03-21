<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
	<head>
		<%@ include file="/views/fragments/calendar.jsp" %>
		<script type="text/javascript" src='<s:url value="/scripts/jquery/jquery.js" />'></script>

		<script type="text/javascript">
			var nbSingleDishDivs = <s:property value="model.selectedFoodIds.length" />;
			var maxSingleDishDivIndex = nbSingleDishDivs - 1;

			function addSingleDishDiv() {
				maxSingleDishDivIndex++;
				nbSingleDishDivs++;

				var newSingleDishDiv = $('#mockSingleDishDiv').clone()
					.attr('id', 'singleDish-' + maxSingleDishDivIndex).get();
				$('a:first', newSingleDishDiv).attr('onClick', 'javascript:removeSingleDishDiv(' + maxSingleDishDivIndex + ')');
				$(newSingleDishDiv).insertBefore('#addSingleDishLinkDiv').show();
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
		<h2><s:text name="meals.edit.title" /></h2>

		<s:actionerror />

		<s:form namespace="/meals" action="%{model.mealId == null ? 'createSubmit' : 'updateSubmit'}">
			<s:hidden name="model.mealId" />
			<s:hidden name="model.eaterId" />

			<div class="required">
				<label><s:text name="meal.instant" />:</label>
				<s:date id="instant" name="model.instant" format="yyyy-MM-dd hh:mm:ss" />
				<s:textfield id="f_date_c" key="meal.instant" name="model.instant" readonly="1" />
				<img
					id="f_trigger_c"
					src="<s:url value='/scripts/jscalendar/img.gif' />"
					style="cursor: pointer; border: 1px solid red;"
					title="Date selector"
					onmouseover="this.style.background='red';"
					onmouseout="this.style.background=''"
				/>
			</div>

			<fieldset id="dishes">
				<legend><s:text name="meal.dishes" /></legend>

				<s:iterator value="model.selectedFoodIds" status="it">
					<div id="singleDish-${it.index}">
						<div>
							<label><s:text name="meal.dish" />:</label>
							<s:select
								name="model.selectedFoodIds"
								value="model.selectedFoodIds[#it.index]"
								list="availableFoods"
								listKey="id"
								listValue="name"
							/>
						</div>

						<div>
							<label><s:text name="preparationMethod" />:</label>
							<s:select
								name="model.selectedPreparationMethodNames"
								value="model.selectedPreparationMethodNames[#it.index]"
								list="availablePreparationMethods"
								listKey="name()"
								listValue="%{getText('preparationMethod.' + name())}"
							/>
						</div>

						<div>
							<a href="#" onClick="javascript:removeSingleDishDiv(${it.index})"><s:text name="meals.edit.removeDish" /></a>
						</div>
					</div>
				</s:iterator>

				<div id="addSingleDishLinkDiv">
					<a href="#" onClick="javascript:addSingleDishDiv()"><s:text name="meals.edit.addDish" /></a>
				</div>
			</fieldset>

			<div class="actions">
				<s:submit cssClass="button" key="general.submit" />
				<s:submit cssClass="button" key="general.cancel" name="method:cancel" />
			</div>
		</s:form>

		<%-- Mock dish div for cloning (not displayed) --%>
		<div id="mockSingleDishDiv" style="display: none">
			<div>
				<label><s:text name="meal.dish" />:</label>
				<s:select
					name="model.selectedFoodIds"
					list="availableFoods"
					listKey="id"
					listValue="name"
				/>
			</div>

			<div>
				<label><s:text name="preparationMethod" />:</label>
				<s:select
					name="model.selectedPreparationMethodNames"
					list="availablePreparationMethods"
					listKey="name()"
					listValue="%{getText('preparationMethod.' + name())}"
				/>
			</div>

			<div>
				<a href="#" onClick="javascript:removeSingleDishDiv(-1)"><s:text name="meals.edit.removeDish" /></a>
			</div>
		</div>

		<script type="text/javascript">
			$(document).ready(function() {
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

				if (nbSingleDishDivs <= 0) {
					addSingleDishDiv();
				}
			});
		</script>
	</body>
</html>