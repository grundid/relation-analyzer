<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="includes/taglibs.jspf"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="includes/head.jspf"%>
<!-- Leaflet CSS -->
<link rel="stylesheet" href="rs/leaflet/leaflet.css" />
<!--[if lte IE 8]><link rel="stylesheet" href="rs/leaflet/leaflet.ie.css" /><![endif]-->

<!-- Leaflet JavaScript -->
<script src="rs/leaflet/leaflet-src.js"></script>

</head>

<body>
	<%@ include file="includes/header.jspf"%>
	<div class="container">
		<div id="map" style="position: absolute; left: 5px; right: 5px; bottom: 5px; top: 46px;"></div>
		<%@ include file="includes/footer.jspf"%>
	</div>
	<!-- /container -->
	<%@ include file="includes/javascript.jspf"%>
	<script src="rs/ra.js"></script>
	<script type="text/javascript">
		$.ajax({
			url : 'showRelation?relationId=${report.relationInfo.relationId}',
			dataType : 'json',
			error : function(event, xhr, options, exc) {
				alert(event.statusText);
			},
			success : function(data) {
				if (data.features && data.features.length) {
					geojsonLayer.addGeoJSON(data);
					
					if (data.bbox) {
						var southWest = new L.LatLng(data.bbox[1],data.bbox[0]);
						var northEast = new L.LatLng(data.bbox[3],data.bbox[2]);
						map.fitBounds(new L.LatLngBounds(southWest, northEast));
					}
					
					
				} else
					alert("Relation empty");
			}
		});
	</script>

</body>
</html>
