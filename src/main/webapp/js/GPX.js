/* Copyright (c) 2006 MetaCarta, Inc., published under the BSD license.
 * See http://svn.openlayers.org/trunk/openlayers/release-license.txt 
 * for the full text of the license. */


/**
 * @class
 * 
 * @requires OpenLayers/Layer/Markers.js
 * @requires OpenLayers/Ajax.js
 */
OpenLayers.Layer.GPX = OpenLayers.Class.create();
OpenLayers.Layer.GPX.prototype = 
  OpenLayers.Class.inherit( OpenLayers.Layer.Markers, OpenLayers.Layer.Vector, {

    /** store url of text file
    * @type str */
    url:null,
    icolor:null,
    /** @type Array(OpenLayers.Feature) */
    features: null,

    /** @type OpenLayers.Feature */
    selectedFeature: null,

    /**
    * @constructor
    *
    * @param {String} name
    * @param {String} location
    */
    initialize: function(name, url, icolor, options) {
	var newArguments = new Array()
	newArguments.push(name, options);
	OpenLayers.Layer.Vector.prototype.initialize.apply(this, newArguments);
        OpenLayers.Layer.Markers.prototype.initialize.apply(this, [name]);
        this.url = url;
        this.icolor = icolor;
        this.features = new Array();
	var results = OpenLayers.loadURL(this.url, null, this, this.requestSuccess, this.requestFailure);
    },

    /**
     * 
     */

    destroy: function() {
        this.clearFeatures();
        this.features = null;
        OpenLayers.Layer.Markers.prototype.destroy.apply(this, arguments);
    },
        
    /**
     * @param {XMLHttpRequest} ajaxRequest
     */
    requestSuccess:function(request) {
	var gpxns = "http://www.topografix.com/GPX/1/0";
        var doc = request.responseXML;
        if (!doc || request.fileType!="XML") {
            doc = OpenLayers.parseXMLString(request.responseText);
        }
        if (typeof doc == "string") {
            doc = OpenLayers.parseXMLString(doc);
        }
	
	/* search and display track */
//	var trk = OpenLayers.Ajax.getElementsByTagNameNS(doc, gpxns, "", "trk");
  var trk = doc.getElementsByTagName("trk");	
	var featureTRK = [];
	for (var i = 0; i< trk.length; i++) {
//    var color = '#00FF00';         // Fix Color
//    var color=this.randomColor();  // Random Color
      var color=this.icolor;  // Random Color
		for (var j = 0; j < trk[i].childNodes.length; j++) {
                        switch (trk[i].childNodes[j].nodeName)
                        {
                                case 'topografix:color':
                                        color = '#' + OpenLayers.Util.getXmlNodeValue(trk[i].childNodes[j]);
                                        break;
                                case 'trkseg':
					if (color == '')
						color=this.randomColor();
                                        featureTRK.push(this.addLineGPX(trk[i].childNodes[j], color));
                                        break;
                                case '#text':
                                        break;
                                case 'name':
//FIXME: label the way
                                        break;
                                default:
//                                      alert('unknown ' + trk[i].childNodes[j].nodeName);
                                        break;
                        }
                }
        }
	this.addFeatures(featureTRK);
	/* search and display route */
//	var rte = OpenLayers.Ajax.getElementsByTagNameNS(doc, gpxns, "", "rte");
  var rte = doc.getElementsByTagName("rte");		
	var featureRTE = [];
	for (var i = 0; i< rte.length; i++) {
		var color = this.randomColor();
		var style_green = {
			strokeColor: color,
			strokeOpacity: 1,
			strokeWidth: 4,
			pointRadius: 6,
			pointerEvents: "visiblePainted"
		};
		var pointList = [];
		for (var j = 0; j < rte[i].childNodes.length; j++) {
			switch (rte[i].childNodes[j].nodeName)
			{
				case 'rtept':
					var feature = this.parseFeature(rte[i].childNodes[j]);
					if (feature) {
						pointList.push(feature);
					}
					break;
				default:
					break;
			}
                }
		featureRTE.push(new OpenLayers.Feature.Vector(new OpenLayers.Geometry.LineString(pointList),null,style_green));
        };
	this.addFeatures(featureRTE);

	/* search and display waypoint */
//	var wpt = OpenLayers.Ajax.getElementsByTagNameNS(doc, gpxns, "", "wpt");
  var wpt = doc.getElementsByTagName("wpt");		
        var featuresWPT = [];
        for (var i = 0; i< wpt.length; i++) {
		var data = {}; 
		var point = this.setToMercator(wpt[i].getAttribute('lon'),wpt[i].getAttribute('lat'));
		var location =  new OpenLayers.LonLat(point[0], point[1]);
		/* Provide defaults for title and description */
		var title = "Untitled";
		try {
			title = OpenLayers.Util.getNodes(wpt[i], 
				'name')[0].firstChild.nodeValue;
		}
		catch (e) { title="Untitled"; }

		/* GPX descriptions */
		var description = "No description.";
		try {
			description = OpenLayers.Util.getNodes(wpt[i],
				'desc')[0].firstChild.nodeValue;
		}catch (e) {
			var description = "No description.";
		}
		data.icon = OpenLayers.Marker.defaultIcon();
//		data.popupSize = new OpenLayers.Size(250, 120);
		if ((title != null) && (description != null)) {
//                if (link) contentHTML += '<a class="link" href="'+link+'" target="_blank">';
//			contentHTML += '<p>' + title + '</p>';
//                if (link) contentHTML += '</a>';
			contentHTML = '<p><strong>' + title + '</strong><br />' + description + '</p>';
			data['popupContentHTML'] = contentHTML;
		}
		var featureWPT = new OpenLayers.Feature(this, location, data);
            featuresWPT.push(featureWPT);
              var marker = featureWPT.createMarker();
   	     marker.events.register('click', featureWPT, this.markerClick);
              this.addMarker(marker);
        }

//        for (var i = 0; i < itemlist.length; i++) {
//---
//
//            /* If no link URL is found in the first child node, try the
//               href attribute */
//            try {
//              var link = OpenLayers.Util.getNodes(itemlist[i], "link")[0].firstChild.nodeValue;
//            } 
//            catch (e) {
//              try {
//                var link = OpenLayers.Util.getNodes(itemlist[i], "link")[0].getAttribute("href");
//              }
//              catch (e) {}
//            }
//        }
    },
    
    possibleColors : ["#FF0000", "#0000FF", "#00FF00", "#FFAE00"],
    currentColor : 0,
    randomColor : function()
    {
    	this.currentColor = (this.currentColor + 1) % this.possibleColors.length;
    	return this.possibleColors[this.currentColor];
    },
    
    
    /**
     * @param {Event} evt
     */
    randomColorOld: function() {
	var hex=new Array("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F");
	var color = '#';
	for (i=0;i<6;i++){
		color += hex[Math.floor(Math.random()*hex.length)];
	}
	return color;
    },
    markerClick: function(evt) {
        sameMarkerClicked = (this == this.layer.selectedFeature);
        this.layer.selectedFeature = (!sameMarkerClicked) ? this : null;
        for(var i=0; i < this.layer.map.popups.length; i++) {
            this.layer.map.removePopup(this.layer.map.popups[i]);
        }
        if (!sameMarkerClicked) {
            var popup = this.createPopup(true);
            OpenLayers.Event.observe(popup.div, "click",
            function() { 
              for(var i=0; i < this.layer.map.popups.length; i++) { 
                this.layer.map.removePopup(this.layer.map.popups[i]); 
              } 
            }.bindAsEventListener(this));
            this.layer.map.addPopup(popup); 
        }
        OpenLayers.Event.stop(evt);
    },
    addLineGPX: function(xmlNode, color) {
        var style_green = {
                strokeColor: color,
                strokeOpacity: 0.6,
                strokeWidth: 5,
                pointRadius: 6,
                pointerEvents: "visiblePainted"
        };
        var pointList = [];
        for (var i = 0; i < xmlNode.childNodes.length; i++) {
//	for (var i = 0; i < 4; i++) {
                if (xmlNode.childNodes[i].nodeName == "trkpt")
                {
                        var feature = this.parseFeature(xmlNode.childNodes[i]);
                        if (feature) {
                                pointList.push(feature);
                        }
                }
        };
        return new OpenLayers.Feature.Vector(new OpenLayers.Geometry.LineString(pointList),null,style_green);
    },

     /**
      * This function is the core of the GPX parsing code in OpenLayers.
      * It creates the geometries that are then attached to the returned
      * feature, and calls parseAttributes() to get attribute data out.
      * @param DOMElement xmlNode
      */
     parseFeature: function(xmlNode) {
        if (xmlNode.getAttribute('lat') && xmlNode.getAttribute('lon')) {
		var point = this.setToMercator(xmlNode.getAttribute('lon'),xmlNode.getAttribute('lat'));
		return new OpenLayers.Geometry.Point(point[0], point[1]);
        }
        return false;
    },
    setToMercator: function(lon, lat) {
    	x = parseFloat(lon);
	y = parseFloat(lat);
	var PI = 3.14159265358979323846;
	x = x * 20037508.34 / 180;
	y = Math.log (Math.tan ((90 + y) * PI / 360)) / (PI / 180);
	y = y * 20037508.34 / 180;
	return new Array(x,y);
    },
    /**
     * 
     */
    clearFeatures: function() {
        if (this.features != null) {
            while(this.features.length > 0) {
                var feature = this.features[0];
                OpenLayers.Util.removeItem(this.features, feature);
                feature.destroy();
            }
        }        
    },
    requestFailure: function(request) {
    },
    moveTo:function(bounds, zoomChanged, dragging) { 
	OpenLayers.Layer.Vector.prototype.moveTo.apply(this, arguments);
//	OpenLayers.Layer.Markers.prototype.moveTo.apply(this, arguments);
        if (!dragging) {
            this.div.style.left = - parseInt(this.map.layerContainerDiv.style.left) + "px";
            this.div.style.top = - parseInt(this.map.layerContainerDiv.style.top) + "px";
            var extent = this.map.getExtent();
            this.renderer.setExtent(extent);
	    for(i=0; i < this.markers.length; i++) {
		marker = this.markers[i];
		lonlat = this.map.getLayerPxFromLonLat(marker.lonlat);
                if (marker.icon.calculateOffset) {
                    marker.icon.offset = marker.icon.calculateOffset(marker.icon.size);
                }
                var offsetPx = lonlat.offset(marker.icon.offset);
		marker.icon.imageDiv.style.left = offsetPx.x+parseInt(this.map.layerContainerDiv.style.left) + "px";
		marker.icon.imageDiv.style.top = offsetPx.y+parseInt(this.map.layerContainerDiv.style.top) + "px";
	    }
		
        }
        if (!this.drawn || zoomChanged) {
            this.drawn = true;
            for(var i = 0; i < this.features.length; i++) {
                var feature = this.features[i];
                this.drawFeature(feature);
            }
        }

    },
    setMap: function(map) {
//		OpenLayers.Layer.Markers.prototype.setMap.apply(this, arguments);
        OpenLayers.Layer.prototype.setMap.apply(this, arguments);

        if (!this.renderer) {
            this.map.removeLayer(this);
        } else {
            this.renderer.map = this.map;
            this.renderer.setSize(this.map.getSize());
        }

    },
    /** @final @type String */
    CLASS_NAME: "OpenLayers.Layer.GPX"
});
     
    
