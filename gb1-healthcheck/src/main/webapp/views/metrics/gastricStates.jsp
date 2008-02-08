<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
	<head>
		<script type='text/javascript' src='<c:url value="/dwr/interface/ManageGastricStatesAction.js" />'></script>
		<script type='text/javascript' src='<c:url value="/dwr/engine.js" />'></script>
		<script type='text/javascript' src='<c:url value="/dwr/util.js" />'></script>

		<%@ include file="/includes/calendar.jsp" %>
		<script type="text/javascript" src='<c:url value="/scripts/date.js" />'></script>
		<script type="text/javascript" src='<c:url value="/scripts/jquery/jquery.js" />'></script>

		<script type="text/javascript">
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
				ManageGastricStatesAction.loadGastricStates(patientId, date, showGastricStates);
			}

			function initGastricStates() {
				removeAllDisplayedGastricStates();
				addSingleGastricState(nbDisplayedGastricStates, null);
				showNewGastricStateLink();
			}

			function showGastricStates(states) {
				hideMessages();
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
				newStateDiv.id = 'gastricState-' + index;
				newStateDiv.style.display = 'block';

				var newStateInstant = newStateDiv.getElementsByTagName('input')[0];
				newStateInstant.id = 'gastricStateInstant-' + index;

				var newStateLevel = newStateDiv.getElementsByTagName('select')[0];
				newStateLevel.id = 'gastricStateLevel-' + index;

				if (state == null) {
					newStateInstant.value = formatDate(selectedDate, 'HH:mm');
				}
				else {
					newStateInstant.value = formatDate(state.instant, 'HH:mm');

					var levelOptions = newStateDiv.getElementsByTagName('option');
					for (var i = 0; i < levelOptions.length; i++) {
						if (levelOptions[i].value == state.state) {
							levelOptions[i].selected = 'selected';
						}
					}
				}

				var newStateDivSave = newStateDiv.getElementsByTagName('input')[1];
				newStateDivSave.style.display = 'block';
				newStateDivSave.setAttribute('onClick', 'javascript:saveGastricState(' + index + ')');

				document.getElementById('gastricStates').appendChild(newStateDiv);
				nbDisplayedGastricStates++;
			}

			function showInitGastricStatesLink() {
				$('#noGastricStatesMessage').show();
				document.getElementById('addGastricStateLink').setAttribute('onClick', 'javascript:initGastricStates()');
			}

			function showNewGastricStateLink() {
				$('#noGastricStatesMessage').hide();
				document.getElementById('addGastricStateLink').setAttribute('onClick', 'javascript:addSingleGastricState(' + nbDisplayedGastricStates + ', null)');
			}

			function saveGastricState(index) {
				if (index != -1) {
					var time = document.getElementById('gastricStateInstant-' + index).value;
					var instantText = formatDate(selectedDate, 'yyyy-MM-dd') + ' ' + time;
					var instant = getDateFromFormat(instantText, 'yyyy-MM-dd HH:mm');

					var level = document.getElementById('gastricStateLevel-' + index).value;

					showGastricStateSaving();
					ManageGastricStatesAction.saveGastricState(patientId, instant, level, gastricStateSaved);
				}
			}

			function gastricStateSaved() {
				showGastricStateSaved();
			}

			function showGastricStateSaving() {
				hideMessages();
				$('#gastricStateSaving').show();
			}

			function showGastricStateSaved() {
				hideMessages();
				$('#gastricStateSaved').show();
			}

			function hideMessages() {
				$('#gastricStateSaving').hide();
				$('#gastricStateSaved').hide();
			}
		</script>
	</head>

	<body>
		<h2><fmt:message key="metrics.gastricStates.manage.title" /></h2>

		<div id="gastricStateSaving" style="display: none">
			<fmt:message key="metrics.gastricStates.saving" />
		</div>
		<div id="gastricStateSaved" style="display: none">
			<fmt:message key="metrics.gastricStates.saved" />
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
			<div id="noGastricStatesMessage">
				<fmt:message key="metrics.gastricStates.manage.noStatesOnSelectedDay" />
			</div>

			<div id="gastricStates">
			</div>

			<div>
				<a id="addGastricStateLink" href="#" onClick="javascript:initGastricStates()">
					<fmt:message key="metrics.gastricStates.manage.addState" />
				</a>
			</div>
		</div>

		<%-- Mock gastric state for cloning (not displayed) --%>
		<div id="mockGastricState" style="display: none">
			<div>
				<label><fmt:message key="gastricState.instant" />:</label>
				<input type="text" id="mockGastricStateInstant" />
			</div>
			<div>
				<label><fmt:message key="gastricState.level" />:</label>
				<select id="mockGastricStateLevel">
					<option value="NORMAL"><fmt:message key="gastricState.level.normal" /></option>
					<option value="SLIGHTLY_BLOATED"><fmt:message key="gastricState.level.slightlyBloated" /></option>
					<option value="BLOATED"><fmt:message key="gastricState.level.bloated" /></option>
					<option value="HIGHLY_BLOATED"><fmt:message key="gastricState.level.highlyBloated" /></option>
					<option value="CRISIS"><fmt:message key="gastricState.level.crisis" /></option>
				</select>
			</div>
			<div class="actions">
				<input class="button" type="submit" onClick="javascript:saveGastricState(-1)" value="<fmt:message key='metrics.gastricStates.save' />" />
			</div>
		</div>

		<script type="text/javascript">
			$(document).ready(function() {
				loadGastricStates(patientId, selectedDate);
			});
		</script>
	</body>
</html>
