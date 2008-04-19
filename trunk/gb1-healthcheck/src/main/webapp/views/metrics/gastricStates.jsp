<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
	<head>
		<script type='text/javascript' src='<c:url value="/dwr/interface/ManageGastricStatesAction.js" />'></script>
		<script type='text/javascript' src='<c:url value="/dwr/engine.js" />'></script>
		<script type='text/javascript' src='<c:url value="/dwr/util.js" />'></script>

		<%@ include file="/views/fragments/calendar.jsp" %>
		<script type="text/javascript" src='<c:url value="/scripts/core.js" />'></script>
		<script type="text/javascript" src='<c:url value="/scripts/date.js" />'></script>
		<script type="text/javascript" src='<c:url value="/scripts/jquery/jquery.js" />'></script>

		<script type="text/javascript">
			var datePattern = 'yyyy-MM-dd';
			var dateTimePattern = datePattern + ' ' + 'HH:mm';

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
				showStatus('<s:text name="metrics.gastricStates.loading" />');
				ManageGastricStatesAction.loadGastricStates(patientId, date, showGastricStates);
			}

			function initGastricStates() {
				removeAllDisplayedGastricStates();
				addSingleGastricState(nbDisplayedGastricStates, null);
				showNewGastricStateLink();
			}

			function showGastricStates(states) {
				showStatus('<s:text name="metrics.gastricStates.loaded" />');

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
				var newStateDiv = $('#mockGastricState').clone().attr('id', 'gastricState-' + index).get();

				var newStateTimeSelect = $('select:eq(0)', newStateDiv).attr('id', 'gastricStateTime-' + index).get();
				var stateTime = formatDate((state == null ? selectedDate : state.instant), 'HH:00');

				$('option', newStateTimeSelect).each(function(idx, option) {
					option.selected = (option.value == stateTime ? 'selected' : '');
				});

				var newStateLevel = $('select:eq(1)', newStateDiv).attr('id', 'gastricStateLevel-' + index).get();
				var stateLevel = (state == null ? 0 : state.state);

				$('option', newStateLevel).each(function(idx, option) {
					option.selected = (option.value == stateLevel ? 'selected' : '');
				});

				$('a', newStateDiv).attr('onClick', 'javascript:saveGastricState(' + index + ')');
				$(newStateDiv).appendTo("#gastricStates").show();

				nbDisplayedGastricStates++;
			}

			function showInitGastricStatesLink() {
				showStatus('<s:text name="metrics.gastricStates.manage.noStatesOnSelectedDay" />');
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

					showStatus('<s:text name="metrics.gastricStates.saving" />');
					ManageGastricStatesAction.saveGastricState(patientId, instant, level, gastricStateSaved);
				}
			}

			function gastricStateSaved() {
				showStatus('<s:text name="metrics.gastricStates.saved" />');
			}

			function showStatus(msg) {
				$('#status').html(msg);
			}
		</script>
	</head>

	<body>
		<h2><s:text name="metrics.gastricStates.manage.title" /></h2>

		<div id="status"></div>

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
					<s:text name="metrics.gastricStates.manage.addState" />
				</a>
			</div>
		</div>

		<%-- Mock gastric state for cloning (not displayed) --%>
		<div id="mockGastricState" style="display: none">
			<label><s:text name="gastricState.instant" />:</label>
			<select id="mockGastricStateTime">
				<c:forEach begin="0" end="23" var="hour">
					<fmt:formatNumber var="strHour" minIntegerDigits="2" value="${hour}" />
					<option value="${strHour}:00">${strHour}:00</option>
				</c:forEach>
			</select>

			<label><s:text name="gastricState.level" />:</label>
			<select id="mockGastricStateLevel">
				<option value="NORMAL"><s:text name="gastricState.level.normal" /></option>
				<option value="SLIGHTLY_BLOATED"><s:text name="gastricState.level.slightlyBloated" /></option>
				<option value="BLOATED"><s:text name="gastricState.level.bloated" /></option>
				<option value="HIGHLY_BLOATED"><s:text name="gastricState.level.highlyBloated" /></option>
				<option value="CRISIS"><s:text name="gastricState.level.crisis" /></option>
			</select>

			<a id="submitLink" href="#" onclick="javascript:saveGastricState(-1);"><s:text name='metrics.gastricStates.save' /></a>
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
