<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html >
<html>
<head>
<title>OSM Relation Analyzer</title>
<meta name="author" content="Adrian Stabiszewski, Multimedia und IT">
<meta name="description" lang="en-us" content="Analyzes OpenStreetMap Relations for gaps. Exports Relations to GPX and KML.">
<meta name="keywords" content="OSM openstreetmap relation gap export gpx kml">
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<link href="ra.css" rel="stylesheet" type="text/css"> 

</head>
<body>
<div style="text-align:center;width:100%">
<div class="maindiv">

<%@ include file="header.jspf" %>
<div class="introduction">
<spring:message code="ra.intro" htmlEscape="false"/>
</div>

<form:form action="analyzeRelation" modelAttribute="analyzeRelationModel" method="get">
Relation ID: <form:input path="relationId"/>
&nbsp;<button><spring:message code="button.analyze" /></button>
</form:form>
<hr>
<%@ include file="footer.jspf" %>
</div>
</div>
</body>
</html>