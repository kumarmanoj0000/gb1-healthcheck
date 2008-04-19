<%--
 | This is the main decorator for all HealthCheck pages.
 | It includes standard caching, style sheet, header, footer and copyright notice.
 --%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">

<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<html>
	<head>
		<title><decorator:title default="HealthCheck" /></title>

		<meta http-equiv="Cache-Control" content="no-store" />
		<meta http-equiv="Pragma" content="no-cache" />
		<meta http-equiv="Expires" content="0" />
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />

		<link rel="stylesheet" type="text/css" href="<c:url value="/styles/global.css" />"></link>

		<decorator:head />
	</head>

	<body>
		<a name="top"></a>

		<div id="container">
			<div id="header"><%@ include file="/views/fragments/header.jsp"%></div>

			<security:authorize ifAnyGranted="ROLE_STANDARD,ROLE_ADMINISTRATOR">
				<div id="navigation"><%@ include file="/views/fragments/navigation.jsp" %></div>
				<div id="content"><decorator:body /></div>
			</security:authorize>
			<security:authorize ifNotGranted="ROLE_STANDARD,ROLE_ADMINISTRATOR">
				<div id="login"><decorator:body /></div>
			</security:authorize>

			<div id="footer"><%@ include file="/views/fragments/footer.jsp"%></div>
		</div>
	</body>
</html>
