<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html >
<html>
<head>
<title>OSM Relation Analyzer</title>
<link href="ra.css" rel="stylesheet" type="text/css"> 
</head>
<body>
<c:forEach items="${analyzerReport.reportItems}" var="reportItem">

<h3>${reportItem.role}</h3>

<c:forEach items="${reportItem.relationItems}" var="relationItem">
<div class="relation-item">

<div class="start-nodes">
<c:forEach items="${relationItem.startNodes}" var="node">
${node.id}, 
</c:forEach>
</div>

<div class="end-nodes">
<c:forEach items="${relationItem.endNodes}" var="node">
${node.id}, 
</c:forEach>
</div>

</div>

</c:forEach>



</c:forEach>
</body>
</html>