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
				var statesDiv = document.getElementById('gastricStates');
				while (statesDiv.hasChildNodes()) {
					statesDiv.removeChild(statesDiv.firstChild);
				}

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

				var newStateDivSave = newStateDiv.getElementsByTagName('a')[0];
				newStateDivSave.style.display = 'block';
				newStateDivSave.setAttribute('onClick', 'javascript:saveGastricState(' + index + ')');

				document.getElementById('gastricStates').appendChild(newStateDiv);
				nbDisplayedGastricStates++;
			}

			function showInitGastricStatesLink() {
				document.getElementById('noGastricStatesMessage').style.display = 'block';
				document.getElementById('addGastricStateLink').setAttribute('onClick', 'javascript:initGastricStates()');
			}

			function showNewGastricStateLink() {
				document.getElementById('noGastricStatesMessage').style.display = 'none';
				document.getElementById('addGastricStateLink').setAttribute('onClick', 'javascript:addSingleGastricState(' + nbDisplayedGastricStates + ', null)');
			}

			function saveGastricState(index) {
				if (index != -1) {
					// specify seconds, otherwise they are inferred and not always equal to 00
					var time = document.getElementById('gastricStateInstant-' + index).value + ':00';
					var instantText = formatDate(selectedDate, 'yyyy-MM-dd') + ' ' + time;
					var instant = getDateFromFormat(instantText, 'yyyy-MM-dd HH:mm:ss');

					var level = document.getElementById('gastricStateLevel-' + index).value;

					ManageGastricStatesAction.saveGastricState(patientId, instant, level, gastricStateSaved);
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
				<a href="#" onClick="javascript:saveGastricState(-1)"><fmt:message key="metrics.gastricStates.save" /></a>
			</div>
		</div>

		<script type="text/javascript">
			loadGastricStates(patientId, selectedDate);
		</script>
	</body>
</html>
