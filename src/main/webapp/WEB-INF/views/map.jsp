<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<title>Relation</title>
	<link href="ra.css" rel="stylesheet" type="text/css"> 
	<!-- bring in the OpenLayers javascript library
		 (here we bring it from the remote site, but you could
		 easily serve up this javascript yourself) -->
	<script src="http://www.openlayers.org/api/OpenLayers.js"></script>
	<!-- bring in the OpenStreetMap OpenLayers layers.
		 Using this hosted file will make sure we are kept up
		 to date with any necessary changes -->
<script src="http://www.openstreetmap.org/openlayers/OpenStreetMap.js"></script>
<script src="GPX.js"></script>
	<script type="text/javascript">
		// Start position for the map (hardcoded here for simplicity,
		// but maybe you want to get from URL params)

		var lat= 49.9;
		var lon= 9.8;
		var zoom=13;
		
		var map; //complex object of type OpenLayers.Map

		//Initialise the 'map' object
		function init() {
			map = new OpenLayers.Map ("map", {
				controls:[
					new OpenLayers.Control.Navigation(),
					new OpenLayers.Control.PanZoomBar(),
					new OpenLayers.Control.LayerSwitcher(),
					new OpenLayers.Control.Attribution()],
				maxExtent: new OpenLayers.Bounds(-20037508.34,-20037508.34,20037508.34,20037508.34),
							maxResolution: 156543.0399,
				numZoomLevels: 19,
				units: 'm',
				projection: new OpenLayers.Projection("EPSG:900913"),
				displayProjection: new OpenLayers.Projection("EPSG:4326")
			} );


			// Define the map layer
			// Note that we use a predefined layer that will be
			// kept up to date with URL changes
			// Here we define just one layer, but providing a choice
			// of several layers is also quite simple
			// Other defined layers are OpenLayers.Layer.OSM.Mapnik, OpenLayers.Layer.OSM.Maplint and OpenLayers.Layer.OSM.CycleMap
			layerMapnik = new OpenLayers.Layer.OSM.Mapnik("Mapnik");
			map.addLayer(layerMapnik);
			layerTilesAtHome = new OpenLayers.Layer.OSM.Osmarender("Osmarender");
			map.addLayer(layerTilesAtHome);
			layerCycleMap = new OpenLayers.Layer.OSM.CycleMap("CycleMap");
			map.addLayer(layerCycleMap);
			layerMarkers = new OpenLayers.Layer.Markers("Markers");
			map.addLayer(layerMarkers);

// Add the Layer with GPX Track

			map.addLayer(new OpenLayers.Layer.GPX("RelID ", "", ""));
			
			var lonLat = new OpenLayers.LonLat(lon, lat).transform(new OpenLayers.Projection("EPSG:4326"), map.getProjectionObject());
			map.setCenter (lonLat, zoom);

			
//		layerMarkers.addMarker(new OpenLayers.Marker(new OpenLayers.LonLat(, ).transform(new OpenLayers.Projection("EPSG:4326"), map.getProjectionObject()),new OpenLayers.Icon('marker.png',new OpenLayers.Size(21,25),new OpenLayers.Pixel(-10, -25))));
//		layerMarkers.addMarker(new OpenLayers.Marker(new OpenLayers.LonLat(, ).transform(new OpenLayers.Projection("EPSG:4326"), map.getProjectionObject()),new OpenLayers.Icon('marker-blue.png',new OpenLayers.Size(21,25),new OpenLayers.Pixel(-10, -25))));

		}
	</script>
</head>
<body onload="init();" style="margin:0px">
<div style="padding:10px">
<div class="introduction">
<p>
</div>
</div>
<!-- define a DIV into which the map will appear. Make it take up the whole window -->
<div style="border:1px solid #000000;margin-left: 10px !important;margin-right: 10px !important;height: 80%" id="map"></div>
</body>
</html>