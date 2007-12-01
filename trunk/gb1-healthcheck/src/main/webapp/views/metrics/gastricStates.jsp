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

		<script type="text/javascript">
			var patientId = ${patient.id};
			var selectedDate = new Date();
			var nbDisplayedGastricStates = 0;

			function dateChanged(calendar) {
				if (calendar.dateClicked) {
					selectedDate = calendar.date;
					loadGastricStatesFor(patientId, selectedDate);
				}
			}

			function loadGastricStatesFor(patientId, date) {
				ManageGastricStatesAction.loadGastricStatesFor(patientId, date, showGastricStates);
			}

			function initGastricStates() {
				removeAllDisplayedGastricStates();
				addSingleGastricState(nbDisplayedGastricStates, null);
				addNewGastricStateLink();
			}

			function showGastricStates(states) {
				removeAllDisplayedGastricStates();

				if (states.length == 0) {
					document.getElementById('gastricStates').innerHTML = '<p><fmt:message key="metrics.gastricStates.manage.noStatesOnSelectedDay" /></p>';
					addInitGastricStatesLink();
				}
				else {
					for (var i = 0; i < states.length; i++) {
						addSingleGastricState(i, states[i]);
					}
					addNewGastricStateLink();
				}
			}

			function removeAllDisplayedGastricStates() {
				var statesDiv = document.getElementById('gastricStates');
				while (statesDiv.hasChildNodes()) {
					statesDiv.removeChild(statesDiv.firstChild);
				}

				nbDisplayedGastricStates = 0;
			}

			function addSingleGastricState(index, state) {
				// TODO Remove "new state" link and add it back at the end 

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

				var newStateDivSave = newStateDiv.getElementsByTagName('a')[0];
				newStateDivSave.style.display = 'block';
				newStateDivSave.setAttribute('onClick', 'javascript:saveGastricState(' + index + ')');

				document.getElementById('gastricStates').appendChild(newStateDiv);
				nbDisplayedGastricStates++;
			}

			function addInitGastricStatesLink() {
				var addStateLink = document.getElementById('addGastricStateLink').cloneNode(true);
				addStateLink.style.display = 'inline';
				addStateLink.getElementsByTagName('a')[0].setAttribute('onClick', 'javascript:initGastricStates()');

				document.getElementById('gastricStates').appendChild(addStateLink);
			}

			function addNewGastricStateLink() {
				var addStateLink = document.getElementById('addGastricStateLink').cloneNode(true);
				addStateLink.style.display = 'inline';
				addStateLink.getElementsByTagName('a')[0].setAttribute('onClick', 'javascript:addSingleGastricState(' + nbDisplayedGastricStates + ', null)');

				document.getElementById('gastricStates').appendChild(addStateLink);
			}

			function saveGastricState(index) {
				if (index != -1) {
					var gastricStateDiv = document.getElementById('gastricState-' + index);
					var instant = gastricStateDiv.getElementById('gastricStateInstant-' + index);
					var level = gastricStateDiv.getElementById('gastricStateLevel-' + index);

					// TODO Create a complete instant by joining calendar date and input time
					ManageGastricStatesAction.savePatientGastricState(patientId, instant.value, level.value, gastricStateSaved);
				}
			}

			function gastricStateSaved() {
			}
		</script>
	</head>

	<body>
		<h2><fmt:message key="metrics.gastricStates.manage.title" /></h2>

		<div style="float: left; margin-left: 1em; margin-bottom: 1em;" id="calendar-container"></div>
		<script type="text/javascript">
			Calendar.setup({
				flat			: "calendar-container",
				flatCallback	: dateChanged,
				timeFormat  	: 24,
				step        	: 1
			});
		</script>

		<div id="gastricStates">
		</div>

		<%-- Add gastric state link (not displayed)  --%>
		<p id="addGastricStateLink" style="display: none">
			<a href="#" onClick="javascript:initGastricStates()">
				<fmt:message key="metrics.gastricStates.manage.addState" />
			</a>
		</p>

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
				<a href="#" onClick="javascript:saveGastricState(-1)"><fmt:message key="metrics.gastricStates.save" /></a>
			</div>
		</div>

		<script type="text/javascript">
			loadGastricStatesFor(patientId, selectedDate);
		</script>
	</body>
</html>
