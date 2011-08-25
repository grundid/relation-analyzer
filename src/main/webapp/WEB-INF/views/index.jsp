<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="includes/taglibs.jspf" %>
<!DOCTYPE html >
<html>
<head>
<title>OSM Relation Analyzer</title>
<link href="ra.css" rel="stylesheet" type="text/css"> 
<%@ include file="includes/metaData.jspf" %>
</head>
<body>
<div style="text-align:center;width:100%">
<div class="maindiv">
<%@ include file="includes/header.jspf" %>
<div class="introduction">
<spring:message code="ra.intro" htmlEscape="false"/>
</div>
<%@ include file="includes/analyzeForm.jspf" %>
<hr>
<%@ include file="includes/searchForm.jspf" %>
<%@ include file="includes/footer.jspf" %>
</div>
</div>
</body>
</html>