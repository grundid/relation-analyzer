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

<div>
<spring:bind path="report.relationInfo.timestamp">
<p>Relation ID: ${report.relationInfo.relationId}, Last modified: <spring:transform value="${report.relationInfo.timestamp}"></spring:transform>, by User: ${report.relationInfo.user}</p>
<p>${report.relationRating.rating}</p>
<p><spring:message code="${report.relationRating.messageCode}"></spring:message></p>
</spring:bind>
</div>

<c:forEach items="${report.reportItems}" var="reportItems">
<div>
<c:forEach items="${reportItems.endNodeDistances}" var="endNode">
<div>
<p>NodeId: ${endNode.node.id}</p>

<c:forEach items="${endNode.distances}" var="distanceNode">
${distanceNode.node.id} (${distanceNode.distance})
</c:forEach>


</div>
</c:forEach>
</div>
</c:forEach>


</body>
</html>