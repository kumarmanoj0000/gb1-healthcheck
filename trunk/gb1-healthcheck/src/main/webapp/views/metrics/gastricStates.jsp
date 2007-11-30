<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
	<head>
		<link rel="stylesheet" type="text/css" href="<c:url value='/scripts/jscalendar/calendar-win2k-1.css' />" title="win2k-1"></link>
		<script type="text/javascript" src="<c:url value='/scripts/jscalendar/calendar.js' />"></script>
		<script type="text/javascript" src="<c:url value='/scripts/jscalendar/calendar-setup.js' />"></script>
		<%-- TODO Load the calendar resource file based on locale --%>
		<script type="text/javascript" src="<c:url value='/scripts/jscalendar/lang/calendar-en.js' />"></script>

		<script type='text/javascript' src='/healthcheck/dwr/interface/ManageGastricStatesAction.js'></script>
		<script type='text/javascript' src='/healthcheck/dwr/engine.js'></script>
		<script type='text/javascript' src='/healthcheck/dwr/util.js'></script>

		<script type="text/javascript">
			function dateChanged(calendar) {
				if (calendar.dateClicked) {
					loadGastricStatesFor(${patient.id}, calendar.date);
				}
			}

			function loadGastricStatesFor(patientId, date) {
				ManageGastricStatesAction.loadGastricStatesFor(${patient.id}, date, showGastricStates);
			}

			function showGastricStates(states) {
				var statesDiv = document.getElementById('gastricStates');
				var mockStateDiv = document.getElementById('mockGastricState');

				while (statesDiv.hasChildNodes()) {
					statesDiv.removeChild(statesDiv.firstChild);
				}

				if (states.length == 0) {
					var newStateDiv = mockStateDiv.cloneNode(true);
					newStateDiv.id = 'gastricState-0';
					newStateDiv.style.display = 'block';

					var newStateDivSave = newStateDiv.getElementsByTagName('a')[0];
					newStateDivSave.setAttribute('onClick', 'javascript:saveGastricState(0)');

					statesDiv.appendChild(newStateDiv);
				}
				else {
					for (var i = 0; i < states.length; i++) {
						var newStateDiv = mockStateDiv.cloneNode(true);
						newStateDiv.id = 'gastricState-' + i;

						var newStateInstantText = newStateDiv.getElementById('gastricStateInstant');
						newStateInstantText.value = i;
						var newStateInstantLevel = newStateDiv.getElementById('gastricStateLevel');
						// TODO Select correct level
						var newStateDivSave = newStateDiv.getElementsByTagName('a')[0];
						newStateDivSave.setAttribute('onClick', 'javascript:saveGastricState(' + i + ')');

						newStateDiv.style.display = 'block';
						statesDiv.appendChild(newStateDiv);
					}
				}
			}

			function saveGastricState(index) {
				if (index != -1) {
					var gastricStateDiv = document.getElementById('gastricState-' + index);
					var instant = gastricStateDiv.getElementById('gastricStateInstant');
					var level = gastricStateDiv.getElementById('gastricStateLevel');

					// TODO Create a complete instant by joining date and time
					ManageGastricStatesAction.savePatientGastricState(${patient.id}, instant.value, level.value, gastricStateSaved);
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

		<!-- Mock gastric state for cloning (not displayed) -->
		<div id="mockGastricState" style="display: none">
			<div>
				<label><fmt:message key="gastricState.instant" />:</label>
				<input type="text" id="gastricStateInstant" />
			</div>
			<div>
				<label><fmt:message key="gastricState.level" />:</label>
				<select id="gastricStateLevel">
					<option value="1"><fmt:message key="gastricState.level.normal" /></option>
					<option value="2"><fmt:message key="gastricState.level.slightlyBloated" /></option>
					<option value="3"><fmt:message key="gastricState.level.bloated" /></option>
					<option value="4"><fmt:message key="gastricState.level.highlyBloated" /></option>
					<option value="5"><fmt:message key="gastricState.level.crisis" /></option>
				</select>
			</div>
			<div class="actions">
				<a href="#" onClick="javascript:saveGastricState(-1)"><fmt:message key="metrics.gastricStates.save" /></a>
			</div>
		</div>

		<script type="text/javascript">
			loadGastricStatesFor(${patient.id}, new Date());
		</script>
	</body>
</html>
