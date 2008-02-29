<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

<html>
	<head>
		<script type='text/javascript' src='<c:url value="/dwr/interface/ManageGastricStatesAction.js" />'></script>
		<script type='text/javascript' src='<c:url value="/dwr/engine.js" />'></script>
		<script type='text/javascript' src='<c:url value="/dwr/util.js" />'></script>

		<%@ include file="/includes/calendar.jsp" %>
		<script type="text/javascript" src='<c:url value="/scripts/core.js" />'></script>
		<script type="text/javascript" src='<c:url value="/scripts/date.js" />'></script>
		<script type="text/javascript" src='<c:url value="/scripts/jquery/jquery.js" />'></script>

		<script type="text/javascript">
			var datePattern = 'yyyy-MM-dd';
			var timePattern = 'HH:mm';
			var dateTimePattern = datePattern + ' ' + timePattern;

			var patientId = ${patient.id};
			var selectedDate = new Date();
			var nbDisplayedGastricStates = 0;

			function dateChanged(calendar) {
				if (calendar.dateClicked) {
					selectedDate = calendar.date;
					loadGastricStates(patientId, selectedDate);
				}
			}

			function loadGastricStates(patientId, date) {
				showStatus('<fmt:message key="metrics.gastricStates.loading" />');
				ManageGastricStatesAction.loadGastricStates(patientId, date, showGastricStates);
			}

			function initGastricStates() {
				removeAllDisplayedGastricStates();
				addSingleGastricState(nbDisplayedGastricStates, null);
				showNewGastricStateLink();
			}

			function showGastricStates(states) {
				showStatus('<fmt:message key="metrics.gastricStates.loaded" />');

				removeAllDisplayedGastricStates();

				if (states.length == 0) {
					showInitGastricStatesLink();
				}
				else {
					for (var i = 0; i < states.length; i++) {
						addSingleGastricState(i, states[i]);
					}

					showNewGastricStateLink();
				}
			}

			function removeAllDisplayedGastricStates() {
				$('#gastricStates').empty();
				nbDisplayedGastricStates = 0;
			}

			function addSingleGastricState(index, state) {
				var newStateDiv = document.getElementById('mockGastricState').cloneNode(true);
				var newStateTime = newStateDiv.getElementsByTagName('select')[0];
				var newStateLevel = newStateDiv.getElementsByTagName('select')[1];

				newStateDiv.id = 'gastricState-' + index;
				newStateTime.id = 'gastricStateTime-' + index;
				newStateLevel.id = 'gastricStateLevel-' + index;

				if (state == null) {
					newStateTime.value = formatDate(selectedDate, timePattern);
				}
				else {
					var stateTime = formatDate(state.instant, timePattern);
					var timeOptions = newStateTime.getElementsByTagName('option');
					for (var i = 0; i < timeOptions.length; i++) {
						if (timeOptions[i].value == stateTime) {
							timeOptions[i].selected = 'selected';
						}
					}

					var levelOptions = newStateLevel.getElementsByTagName('option');
					for (var i = 0; i < levelOptions.length; i++) {
						if (levelOptions[i].value == state.state) {
							levelOptions[i].selected = 'selected';
						}
					}
				}

				var newStateDivSave = newStateDiv.getElementsByTagName('a')[0];
				newStateDivSave.setAttribute('onClick', 'javascript:saveGastricState(' + index + ')');

				$('#gastricStates').append(newStateDiv);
				newStateDiv.style.display = 'block';

				nbDisplayedGastricStates++;
			}

			function showInitGastricStatesLink() {
				showStatus('<fmt:message key="metrics.gastricStates.manage.noStatesOnSelectedDay" />');
				$('#addGastricStateLink').unbind(clickEventName).bind(clickEventName, function() {
					initGastricStates();
				});
			}

			function showNewGastricStateLink() {
				showStatus('&nbsp;');
				$('#addGastricStateLink').unbind(clickEventName).bind(clickEventName, function() {
					addSingleGastricState(nbDisplayedGastricStates, null);
				});
			}

			function saveGastricState(index) {
				if (index != -1) {
					var level = $('#gastricStateLevel-' + index).val();
					var time = $('#gastricStateTime-' + index).val();
					var instantText = formatDate(selectedDate, datePattern) + ' ' + time;
					var instant = getDateFromFormat(instantText, dateTimePattern);

					showStatus('<fmt:message key="metrics.gastricStates.saving" />');
					ManageGastricStatesAction.saveGastricState(patientId, instant, level, gastricStateSaved);
				}
			}

			function gastricStateSaved() {
				showStatus('<fmt:message key="metrics.gastricStates.saved" />');
			}

			function showStatus(msg) {
				$('#status').html(msg);
			}
		</script>
	</head>

	<body>
		<h2><fmt:message key="metrics.gastricStates.manage.title" /></h2>

		<div id="status">
		</div>

		<div style="float: left; margin-left: 1em; margin-bottom: 1em;" id="calendar-container"></div>
		<script type="text/javascript">
			Calendar.setup({
				flat			: "calendar-container",
				flatCallback	: dateChanged,
				timeFormat  	: 24,
				step        	: 1
			});
		</script>

		<div id="gastricStatesWorkArea">
			<div id="gastricStates">
			</div>

			<div>
				<a id="addGastricStateLink" href="#">
					<fmt:message key="metrics.gastricStates.manage.addState" />
				</a>
			</div>
		</div>

		<%-- Mock gastric state for cloning (not displayed) --%>
		<div id="mockGastricState" style="display: none">
			<label><fmt:message key="gastricState.instant" />:</label>
			<select id="mockGastricStateTime">
				<c:forEach begin="0" end="23" var="hour">
					<fmt:formatNumber var="strHour" minIntegerDigits="2" value="${hour}" />
					<option value="${strHour}:00">${strHour}:00</option>
				</c:forEach>
			</select>

			<label><fmt:message key="gastricState.level" />:</label>
			<select id="mockGastricStateLevel">
				<option value="NORMAL"><fmt:message key="gastricState.level.normal" /></option>
				<option value="SLIGHTLY_BLOATED"><fmt:message key="gastricState.level.slightlyBloated" /></option>
				<option value="BLOATED"><fmt:message key="gastricState.level.bloated" /></option>
				<option value="HIGHLY_BLOATED"><fmt:message key="gastricState.level.highlyBloated" /></option>
				<option value="CRISIS"><fmt:message key="gastricState.level.crisis" /></option>
			</select>

			<a id="submitLink" href="#" onclick="javascript:saveGastricState(-1);"><fmt:message key='metrics.gastricStates.save' /></a>
		</div>

		<script type="text/javascript">
			$(document).ready(function() {
				$('#addGastricStateLink').bind(clickEventName, function() {
					initGastricStates();
				});
				loadGastricStates(patientId, selectedDate);
			});
		</script>
	</body>
</html>
