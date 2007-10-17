<%--
 % This is the main decorator for all HealthCheck pages.
 % It includes standard caching, style sheet, header, footer and copyright notice.
 --%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">

<%@ page import="org.acegisecurity.context.SecurityContextHolder"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator"%>
<%@ taglib prefix="authz" uri="http://acegisecurity.org/authz"%>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
	<%--
	 | Define a shortcut to the signed in user once (obtaining it from Acegi is tedious)
	 --%>
	<authz:authorize ifNotGranted="role_anonymous">
		<c:set var="userDetails" value="<%= SecurityContextHolder.getContext().getAuthentication().getPrincipal() %>" />
		<c:set var="user" scope="request" value="${userDetails.user}" />
	</authz:authorize>

	<head>
		<title><decorator:title default="HealthCheck" /></title>

		<meta http-equiv="Cache-Control" content="no-store" />
		<meta http-equiv="Pragma" content="no-cache" />
		<meta http-equiv="Expires" content="0" />
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />

		<link rel="stylesheet" type="text/css" href="<c:url value="/styles/global.css" />"></link>
		<script type="text/javascript" src="<c:url value="/scripts/core.js" />"></script>

		<decorator:head />
	</head>

	<body bgcolor="#FFFFFF">
		<a name="top"></a>
		<div id="container">
			<p id="header"><%@ include file="/includes/header.jsp"%></p>
			<p id="content"><decorator:body /></p>
			<p id="footer"><%@ include file="/includes/footer.jsp"%></p>
		</div>
	</body>
</html>
