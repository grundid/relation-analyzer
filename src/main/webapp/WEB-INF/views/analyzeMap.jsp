<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="includes/taglibs.jspf"%>
<!DOCTYPE html >
<html>
<head>
<title>OSM Relation Analyzer</title>
<link href="ra.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="js/OpenLayers.js"></script>
<script type="text/javascript" src="http://www.openstreetmap.org/openlayers/OpenStreetMap.js"></script>
<script type="text/javascript" src="js/GPX.js"></script>
<script type="text/javascript">
	var lat = 49.3;
	var lon = 9.3;
	var zoom = 10;
	
	var markerRotation = ["img/marker-red.png","img/marker-blue.png","img/marker-green.png","img/marker-gold.png",];
	var markerIndex = 0;

	var map; //complex object of type OpenLayers.Map

	function init() {
		map = new OpenLayers.Map ("map", {
			controls:[
				new OpenLayers.Control.Navigation(),
				new OpenLayers.Control.PanZoomBar(),
				new OpenLayers.Control.LayerSwitcher(),
				new OpenLayers.Control.Attribution(),
				new OpenLayers.Control.Permalink()],
			maxExtent: new OpenLayers.Bounds(-20037508.34,-20037508.34,20037508.34,20037508.34),
			numZoomLevels: 19,
			units: 'm',
			projection: new OpenLayers.Projection("EPSG:900913"),
			displayProjection: new OpenLayers.Projection("EPSG:4326")
		} );

		var layerMapnik = new OpenLayers.Layer.OSM.Mapnik("Mapnik");
		map.addLayer(layerMapnik);
		var layerMarkers = new OpenLayers.Layer.Markers("Markers");
		map.addLayer(layerMarkers);

		map.addLayer(new OpenLayers.Layer.GPX("Relation", "exportRelation/gpx?relationId=${report.relationInfo.relationId}", ""));

		var lastMarkerPosition = null;
		<c:forEach items="${report.reportItems}" var="reportItems" varStatus="itemStatus">
		<c:forEach items="${reportItems.endNodeDistances}" var="endNode">
		lastMarkerPosition = new OpenLayers.LonLat(${endNode.node.lon}, ${endNode.node.lat}).transform(new OpenLayers.Projection("EPSG:4326"), map.getProjectionObject());
		layerMarkers.addMarker(new OpenLayers.Marker(lastMarkerPosition,new OpenLayers.Icon(markerRotation[markerIndex],new OpenLayers.Size(21,25),new OpenLayers.Pixel(-10, -25))));
		</c:forEach>
		markerIndex = (markerIndex + 1) % markerRotation.length;
		</c:forEach>
		
		if (lastMarkerPosition != null && location.href.indexOf("lat=") == -1)
		{
			map.setCenter (lastMarkerPosition, zoom);
		}
	};
</script>

<%@ include file="includes/metaData.jspf"%>
</head>
<body onload="init();" style="height:100%">
	<div style="text-align: center; overflow:hidden;height: 100%">
		<div class="maindiv" style="height:100%">
			<%@ include file="includes/header.jspf"%>
			<div id="map" style="height: 70%;"></div>
			<%@ include file="includes/footer.jspf"%>
		</div>
	</div>
</body>
</html>