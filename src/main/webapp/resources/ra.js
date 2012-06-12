var map = new L.Map('map', {
	center : new L.LatLng(49.30, 9.19),
	zoom : 8
});

var tilesUrl = 'http://tiles.osmsurround.org/{z}/{x}/{y}.png';

var ocmLayer = new L.TileLayer(
		'http://{s}.tile.opencyclemap.org/cycle/{z}/{x}/{y}.png',
		{
			maxZoom : 16,
			attribution : 'Tiles created by <a href="http://www.gravitystorm.co.uk">Andy Allan</a> and Dave Stubbs, Map data &copy; <a href="http://www.openstreetmap.org">OpenStreepMap</a> contributors'
		});

var osmLayer = new L.TileLayer(
		'http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png',
		{
			maxZoom : 18,
			attribution : 'Map data &copy; <a href="http://www.openstreetmap.org">OpenStreepMap</a> contributors'
		});

var habLayer = new L.TileLayer(
		'http://toolserver.org/tiles/hikebike/{z}/{x}/{y}.png',
		{
			maxZoom : 18,
			attribution : 'Tiles created by <a href="http://hikebikemap.de/">Hike & Bike Map</a>, Map data &copy; <a href="http://www.openstreetmap.org">OpenStreepMap</a> contributors'
		});

var baseLayers = {
	"OpenStreetMap" : osmLayer,
	"Hike & Bike Map" : habLayer,
	"OpenCycleMap" : ocmLayer
};

map.addLayer(osmLayer);

var geojsonLayer = new L.GeoJSON();

var overlayLayers = {
	"Objects" : geojsonLayer
};

map.addLayer(geojsonLayer);

map.addControl(new L.Control.Layers(baseLayers, overlayLayers));

geojsonLayer.on("featureparse", function(e) {
	if (e.properties && e.properties.style && e.layer.setStyle) {
		e.layer.setStyle(e.properties.style);
	}

	/*
	 * (function(layer, props) { e.layer.on("mouseover", function(e) {
	 * layer.setStyle({ color : "#ffff00" }); });
	 * 
	 * e.layer.on("mouseout", function(e) { layer.setStyle(props.style); });
	 * 
	 * if (options.mobile) { e.layer.on("click", function(e) {
	 * $('body').append(createEditorPopup(props));
	 * addTypeAheadFields(props.objectId); }); }
	 * 
	 * })(e.layer, e.properties);
	 */

	if (e.properties && e.properties.popup) {
		e.layer.bindPopup(e.properties.popup);
	}
});
