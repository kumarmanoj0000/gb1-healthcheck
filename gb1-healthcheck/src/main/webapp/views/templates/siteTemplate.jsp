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

<html>
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

		<decorator:head />
	</head>

	<body>
		<a name="top"></a>

		<div id="container">
			<div id="header"><%@ include file="/views/fragments/header.jsp"%></div>

			<table id="contentTable" width="100%">
				<tr>
					<authz:authorize ifNotGranted="role_anonymous">
						<td width="20%" valign="top">
							<div id="navigation"><%@ include file="/views/fragments/navigation.jsp" %></div>
						</td>
					</authz:authorize>

					<td valign="top">
						<div id="content"><decorator:body /></div>
					</td>
				</tr>
			</table>

			<div id="footer"><%@ include file="/views/fragments/footer.jsp"%></div>
		</div>
	</body>
</html>
