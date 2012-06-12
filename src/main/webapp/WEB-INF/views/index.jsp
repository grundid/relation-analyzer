<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="includes/taglibs.jspf"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="includes/head.jspf"%>
</head>

<body>
	<%@ include file="includes/header.jspf"%>


	<div class="container">
		<div class="hero-unit">
			<h1>OSM Relation Analyzer</h1>
			<p>
				<spring:message code="ra.intro" htmlEscape="false" />
			</p>
<%--			<p>
				<a class="btn btn-primary btn-large"> Learn more </a>
			</p> --%>
		</div>


		<%@ include file="includes/analyzeForm.jspf"%>
		<%@ include file="includes/searchForm.jspf"%>

		<%@ include file="includes/footer.jspf"%>
	</div>
	<!-- /container -->
	<%@ include file="includes/javascript.jspf"%>
</body>
</html>

