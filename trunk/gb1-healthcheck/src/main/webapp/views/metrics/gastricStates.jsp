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
			function dateChanged(calendar) {
				if (calendar.dateClicked) {
				}
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
	</body>
</html>
