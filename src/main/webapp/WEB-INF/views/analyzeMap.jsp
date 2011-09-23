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
	var zoom = 10;
	
	var markerRotation = ["img/marker-red.png","img/marker-blue.png","img/marker-green.png","img/marker-gold.png",];
	var markerIndex = 0;
	var layerMarkers = new OpenLayers.Layer.Markers("Markers");
	var map; 

	function showPopup(feature) {
		if (feature.popup == null) {
			feature.data.popupContentHTML = "<div style=\"padding:5px\">"+feature.popupContent+"<\/div>";
			feature.popup = feature.createPopup();
			map.addPopup(feature.popup);

			feature.popup.updateSize();
		} else {
			feature.popup.show();
		}
		feature.popupVisible = true;
	}

	function hidePopup(feature) {
		feature.popup.hide();
		feature.popupVisible = false;
		feature.popupClicked = false;
	}
	
	
	function createFeature(lonLat, popupContent) {

		var markerIcon = new OpenLayers.Icon(markerRotation[markerIndex],new OpenLayers.Size(21,25),new OpenLayers.Pixel(-10, -25));

		var feature = new OpenLayers.Feature(layerMarkers,
				lonLat, {
					icon : markerIcon
				});
		feature.popupContent = popupContent;
		feature.closeBox = false;
		feature.popupClass = OpenLayers.Class(OpenLayers.Popup.FramedCloud);
		feature.data.overflow = "hidden";
		feature.popupVisible = false;
		feature.popupClicked = false;
		var marker = feature.createMarker();

		feature.markerClick = function(evt) {
			if (this.popupVisible) {
				if (this.popupClicked) {
					hidePopup(this);
				} else {
					this.popupClicked = true;
				}
			} else {
				showPopup(this);
				this.popupClicked = true;
			}
			OpenLayers.Event.stop(evt);
		};
		feature.markerOver = function(evt) {

			document.body.style.cursor = 'pointer';
			showPopup(this);

			OpenLayers.Event.stop(evt);
		};
		feature.markerOut = function(evt) {
			document.body.style.cursor = 'auto';
			if (this.popup != null && !this.popupClicked)
				hidePopup(this);
			OpenLayers.Event.stop(evt);
		};
		marker.events.register("mousedown", feature, feature.markerClick);
		marker.events.register("mouseover", feature, feature.markerOver);
		marker.events.register("mouseout", feature, feature.markerOut);

		return feature;

	}

	
	

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
		layerMapnik.attribution = null;
		map.addLayer(layerMapnik);
		var layerCycleMap = new OpenLayers.Layer.OSM.CycleMap("CycleMap");
		layerCycleMap.attribution = null;
		map.addLayer(layerCycleMap);
		

		map.addLayer(new OpenLayers.Layer.GPX("Relation", "showRelation?relationId=${report.relationInfo.relationId}", ""));
		map.addLayer(layerMarkers);


		var lastMarkerPosition = null;
		var markerIcon = null;
		var feature = null;
		var popupContent = null;
		
		<c:forEach items="${report.reportItems}" var="reportItem" varStatus="itemStatus">
		<c:forEach items="${reportItem.endNodeDistances}" var="endNode">
		lastMarkerPosition = new OpenLayers.LonLat(${endNode.node.lon}, ${endNode.node.lat}).transform(new OpenLayers.Projection("EPSG:4326"), map.getProjectionObject());
		
		
		popupContent = "<p><spring:message code="label.graph"/>&nbsp;${itemStatus.index+1} (<spring:message code="label.graph.length" />: <fmt:formatNumber pattern="#,##0.000">${reportItem.length}</fmt:formatNumber>)</p><spring:message code="editor.links" /><br/><spring:escapeBody javaScriptEscape="true" htmlEscape="false"><osm:editLinks node1="${endNode.node}" node2="${endNode.node}"/></spring:escapeBody>";
		feature = createFeature(lastMarkerPosition, popupContent);
		layerMarkers.addMarker(feature.marker); //new OpenLayers.Marker(lastMarkerPosition,markerIcon)
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
			<div style="height:10%"><%@ include file="includes/header.jspf"%></div>
			<div id="map" style="height:80%;"></div>
			<div style="height:10%"><%@ include file="includes/footer.jspf"%></div>
		</div>
	</div>
</body>
</html>