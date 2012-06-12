<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
<%@ include file="../includes/taglibs.jspf" %>
<!DOCTYPE html >
<html>
<head>
<title>OSM Relation Analyzer</title>
<%@ include file="../includes/metaData.jspf" %>
</head>
<body>
<h1><spring:message code="error.404" htmlEscape="false"/></h1>
<spring:message code="error.404.details" />

<p><a href="<%= request.getContextPath() %>/index">Home</a></p>

</body>
</html>