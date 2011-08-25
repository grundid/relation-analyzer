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
<%@ include file="includes/analyzeForm.jspf" %>

<div>
<spring:bind path="report.relationInfo.timestamp">
<p>Relation ID: ${report.relationInfo.relationId}, Last modified: <spring:transform value="${report.relationInfo.timestamp}"></spring:transform>, by User: <a href="http://www.openstreetmap.org/user/<spring:escapeBody>${report.relationInfo.user}</spring:escapeBody>"><spring:escapeBody>${report.relationInfo.user}</spring:escapeBody></a></p>
<p>${report.relationRating.rating}</p>
<p><spring:message code="${report.relationRating.messageCode}"></spring:message></p>
</spring:bind>
</div>

<c:forEach items="${report.reportItems}" var="reportItems">
<div style="border:1px solid #ccc;margin-bottom:10px">
<p>Unconnected graph</p>
<c:forEach items="${reportItems.endNodeDistances}" var="endNode">
<div>
<p>End Node Id: ${endNode.node.id}</p>

Closest Nodes (Distance in KM):
<c:forEach items="${endNode.distances}" var="distanceNode">
${distanceNode.node.id} (${distanceNode.distance})
</c:forEach>


</div>
</c:forEach>
</div>
</c:forEach>


<%@ include file="includes/footer.jspf" %>
</div>
</div>
</body>
</html>