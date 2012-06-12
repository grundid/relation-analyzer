<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="includes/taglibs.jspf"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="includes/head.jspf"%>
<link href="rs/ra.css" rel="stylesheet">
</head>

<body>
	<%@ include file="includes/header.jspf"%>
	<div class="container">

<%@ include file="includes/analyzeForm.jspf" %>

<div class="well">
<spring:bind path="report.relationInfo.timestamp">
<p>
<spring:message code="label.id" />: <span class="label label-info">${report.relationInfo.relationId}</span>, 
<spring:message code="label.name" />: <span class="label label-info">${report.relationInfo.name}</span>, 
<spring:message code="label.type" />: <span class="label label-info">${report.relationInfo.type}</span>,
<spring:message code="label.relation.length" />: <span class="label label-info"><fmt:formatNumber pattern="#,##0.000">${report.relationInfo.length}</fmt:formatNumber></span>
</p>
<p><spring:message code="label.lastmodified" />: <span class="label label-info" title="<spring:transform value="${report.relationInfo.timestamp}"></spring:transform>"><spring:message code="${report.relationInfo.modifiedModel.messageCode}" arguments="${report.relationInfo.modifiedModel.amount}" /></span>, 
<spring:message code="label.byuser" />: <strong><a href="http://www.openstreetmap.org/user/<spring:escapeBody>${report.relationInfo.user}</spring:escapeBody>"><spring:escapeBody>${report.relationInfo.user}</spring:escapeBody></a></strong></p>
</spring:bind>

<div style="height: 30px">
<a class="btn" href="#" onclick="$('#tags').toggle();"><spring:message code="button.showtags" /></a>
<a class="btn" href="analyzeMap?relationId=${report.relationInfo.relationId}" title="<spring:message code="button.analyze.map.hint" />"><spring:message code="button.analyze.map" /></a>
<a class="btn" href="http://www.openstreetmap.org/browse/relation/${report.relationInfo.relationId}"><spring:message code="button.browse" /></a>
</div>
</div>

<div class="well" id="tags" style="display:none">
<form action="" class="form-horizontal" >
<fieldset>
<legend><spring:message code="edit.tags" /></legend>
<c:forEach items="${report.relationInfo.tags}" var="tag">
<div class="control-group">
<label class="control-label" for="input01"><spring:escapeBody>${tag.name}</spring:escapeBody></label>
<div class="controls">
<input type="text" class="input-xlarge" id="input01" value="<spring:escapeBody>${tag.value}</spring:escapeBody>">
</div>
</div>
</c:forEach>
</fieldset>
<div class="form-actions" title="<spring:message code="not.implemented.yet" />">
     <button class="btn btn-primary" type="submit" disabled="disabled"><spring:message code="edit.button.save.changes" /></button>
     <button class="btn" type="button" disabled="disabled"><spring:message code="edit.button.add.tag" /></button>
     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<button class="btn btn-danger"  type="button" disabled="disabled"><spring:message code="edit.button.delete.relation" /></button>
</div>
</form>
</div>

<div class="alert alert-${report.relationRating.rating.cssClass}">
<h4 class="alert-heading"><spring:message code="rating.${report.relationRating.rating}"/></h4>
<p><spring:message code="${report.relationRating.messageCode}" /></p>
<p><a class="btn" onclick="$('#more-info').toggle();"><spring:message code="button.more.info" /></a></p>
</div>


<div id="more-info" style="display:none" class="well">
<h3><spring:message code="title.rating"/></h3>
<spring:message code="how.it.works" htmlEscape="false"/>
<spring:message code="how.it.works.more" htmlEscape="false"/>
</div>



<c:if test="${not empty report.elevationProfileJson}">
<div class="well">
<h3><spring:message code="title.elevation.profile" /></h3>
<div id="elevationChart" style="width: 100%;height:250px;"></div>
</div>
</c:if>

<c:if test="${report.relationStatistics.length > 0}">
<div class="well">
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


<div class="well">
<h3><spring:message code="title.report"/></h3>
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
	<!-- /container -->

	<%@ include file="includes/javascript.jspf"%>
<c:if test="${not empty report.elevationProfileJson}">
<script type="text/javascript" src="js/jqPlot/jquery.jqplot.min.js"></script>
<link rel="stylesheet" type="text/css" href="js/qjPlot/jquery.jqplot.css" />
<script>
var options = {axesDefaults: {pad:1},seriesDefaults: {showMarker:false},grid: { background: '#fff' }};
var dataArray = [${report.elevationProfileJson}];

$.jqplot('elevationChart',  dataArray, options);
</script>
</c:if>
	
</body>
</html>

