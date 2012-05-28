<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="includes/taglibs.jspf" %>
<!DOCTYPE html >
<html>
<head>
<title>OSM Relation Analyzer</title>
<link href="ra.css" rel="stylesheet" type="text/css"> 
<script type="text/javascript" src="js/jquery-1.6.2.min.js"></script>
<%@ include file="includes/metaData.jspf" %>
</head>
<body>
<div style="text-align:center;width:100%">
<div class="maindiv">
<%@ include file="includes/header.jspf" %>
<%@ include file="includes/analyzeForm.jspf" %>

<div class="content-box relation-info">
<spring:bind path="report.relationInfo.timestamp">
<p>
<spring:message code="label.id" />: <strong>${report.relationInfo.relationId}</strong>, 
<spring:message code="label.name" />: <strong>${report.relationInfo.name}</strong>, 
<spring:message code="label.type" />: <strong>${report.relationInfo.type}</strong>,
<spring:message code="label.relation.length" />: <strong><fmt:formatNumber pattern="#,##0.000">${report.relationInfo.length}</fmt:formatNumber></strong>
</p>
<p><spring:message code="label.lastmodified" />: <strong><spring:transform value="${report.relationInfo.timestamp}"></spring:transform></strong>, 
<spring:message code="label.byuser" />: <strong><a href="http://www.openstreetmap.org/user/<spring:escapeBody>${report.relationInfo.user}</spring:escapeBody>"><spring:escapeBody>${report.relationInfo.user}</spring:escapeBody></a></strong></p>
</spring:bind>

<div style="height: 30px">
<a class="button-link" href="#" onclick="$('#tags').toggle();"><spring:message code="button.showtags" /></a>
<a class="button-link" href="analyzeMap?relationId=${report.relationInfo.relationId}" title="<spring:message code="button.analyze.map.hint" />"><spring:message code="button.analyze.map" /></a>
<a class="button-link" href="http://www.openstreetmap.org/browse/relation/${report.relationInfo.relationId}"><spring:message code="button.browse" /></a>
</div>
<table id="tags" style="display:none">
<c:forEach items="${report.relationInfo.tags}" var="tag">
<tr><td><spring:escapeBody>${tag.name}</spring:escapeBody></td>
<td><spring:escapeBody>${tag.value}</spring:escapeBody></td>
</tr>
</c:forEach>
</table>
</div>

<c:if test="${report.relationStatistics.length > 0}">
<div class="content-box relation-info">
<h3><spring:message code="title.statistics"/></h3>
<div style="overflow:hidden">
<c:forEach items="${report.relationStatistics.distributions}" var="item">
<div style="float:left;width:${item.percent*100}%;height:24px" class="dist-${item.name}" title="${item.name}, <fmt:formatNumber pattern="#,##0.0">${item.percent*100}</fmt:formatNumber>%, length: <fmt:formatNumber pattern="#,##0.00">${item.length}</fmt:formatNumber> KM, ways: ${item.wayCount}"></div>
</c:forEach>
</div>
<div style="clear:both;margin-top:10px"><spring:message code="info.statistics"/><br/>
<spring:message code="legend.statistics"/></div>
</div>
</c:if>

<h3><spring:message code="title.rating"/></h3>
<div class="content-box relation-rating rating-${report.relationRating.rating}">
<p><spring:message code="rating.${report.relationRating.rating}"/></p>
<p><spring:message code="${report.relationRating.messageCode}"></spring:message></p>

</div>
<div class="content-box">
<spring:message code="how.it.works" htmlEscape="false"/>

<div style="height:30px"><a class="button-link" onclick="$('#more-info').toggle();"><spring:message code="button.more.info" /></a></div>
<div id="more-info" style="display:none">
<spring:message code="how.it.works.more" htmlEscape="false"/>
</div>
</div>

<h3><spring:message code="title.report"/></h3>
<div class="content-box">
<c:forEach items="${report.reportItems}" var="reportItem" varStatus="itemStatus">
<div class="graph">
<p><spring:message code="label.graph"/>&nbsp;${itemStatus.index+1} (<spring:message code="label.graph.length" />: <fmt:formatNumber pattern="#,##0.000">${reportItem.length}</fmt:formatNumber>)</p>
<c:forEach items="${reportItem.endNodeDistances}" var="endNode">
<div>
<p><spring:message code="label.end.node.id"/>: <osm:node>${endNode.node.id}</osm:node></p>

<spring:message code="label.closest.nodes"/>:
<c:forEach items="${endNode.distances}" var="distanceNode">
<osm:node>${distanceNode.node.id}</osm:node>&nbsp;[<osm:editLinks node1="${endNode.node}" node2="${distanceNode.node}"/>]&nbsp;(<fmt:formatNumber pattern="#,##0.000">${distanceNode.distance}</fmt:formatNumber>)
</c:forEach>

</div>
</c:forEach>
</div>
</c:forEach>
</div>

<%@ include file="includes/footer.jspf" %>
</div>
</div>
</body>
</html>