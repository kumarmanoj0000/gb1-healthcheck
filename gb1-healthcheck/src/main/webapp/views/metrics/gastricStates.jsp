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
					alert('patient.id=' + ${patient.id} + ', date=' + calendar.date);
					showGastricStatesFor(${patient.id}, calendar.date);
				}
			}

			function showGastricStatesFor(patientId, date) {
				var states[] = ManageGastricStatesAction.loadGastricStatesFor(${patient.id}, date);
				alert('states=' + states);
			}
		</script>
	</head>

	<body>
		<h2><fmt:message key="metrics.gastricStates.manage.title" /></h2>

		<%-- TODO Put styling in CSS --%>
		<div style="float: left; margin-left: 1em; margin-bottom: 1em; border-right: 1px solid;" id="calendar-container"></div>
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

		<jsp:useBean id="now" class="java.util.Date" />
		<script type="text/javascript">
			showGastricStatesFor(${patient.id}, ${now});
		</script>
	</body>
</html>
