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
<%@ include file="includes/searchForm.jspf" %>
<div class="content-box">

<p><spring:message code="label.showing.relation" /> ${result.displayAmount}/${result.totalAmount}. <spring:message code="label.click.to.analyse" /></p>
<c:if test="${result.displayAmount < result.totalAmount}">
<p><spring:message code="warning.refine.search" /></p>
</c:if>

<table style="width:100%">
<tr>
	<th><spring:message code="label.id" /></th>
	<th><spring:message code="label.name" /></th>
	<th><spring:message code="label.type" /></th>
	<th><spring:message code="label.route" /></th>
	<th><spring:message code="label.ref" /></th>
	<th><spring:message code="label.network" /></th>
	<th><spring:message code="label.operator" /></th>
</tr>

<c:forEach items="${result.relations}" var="relation" varStatus="status">
<tr class="<c:if test="${status.index % 2 == 0}">odd</c:if>">
	<td><a href="analyzeRelation?relationId=${relation.relationId}"><spring:escapeBody>${relation.relationId}</spring:escapeBody></a></td>
	<td><spring:escapeBody>${relation.name}</spring:escapeBody></td>
	<td><spring:escapeBody>${relation.relationType}</spring:escapeBody></td>
	<td><spring:escapeBody>${relation.route}</spring:escapeBody></td>
	<td><spring:escapeBody>${relation.ref}</spring:escapeBody></td>
	<td><spring:escapeBody>${relation.network}</spring:escapeBody></td>
	<td><spring:escapeBody>${relation.operator}</spring:escapeBody></td>
</tr>	
</c:forEach>
</table>
</div>
<%@ include file="includes/footer.jspf" %>
</div>
</div>
</body>
</html>