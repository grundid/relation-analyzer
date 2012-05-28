<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
<%@ include file="../includes/taglibs.jspf" %>
<!DOCTYPE html >
<html>
<head>
<title>OSM Relation Analyzer</title>
<link href="ra.css" rel="stylesheet" type="text/css"> 
<%@ include file="../includes/metaData.jspf" %>
</head>
<body>
<div style="text-align:center;width:100%">
<div class="maindiv">
<%@ include file="../includes/header.jspf" %>
<div class="content-box">
<h1><spring:message code="error.404" htmlEscape="false"/></h1>

<spring:message code="error.404.details" />:

</div>
<%@ include file="../includes/footer.jspf" %>
</div>
</div>
</body>
</html>