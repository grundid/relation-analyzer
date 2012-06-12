<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
<%@ include file="../includes/taglibs.jspf" %>
<!DOCTYPE html >
<html>
<head>
<title>OSM Relation Analyzer</title>
<%@ include file="../includes/metaData.jspf" %>
</head>
<body>
<h1><spring:message code="error.message" htmlEscape="false"/></h1>

<spring:message code="error.message.exception" />:
<p><spring:escapeBody><%= exception %></spring:escapeBody></p>

<p><a href="<%= request.getContextPath() %>/index">Home</a></p>

</body>
</html>
