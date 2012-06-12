package org.osmsurround.ra.export;

import java.io.OutputStream;
import java.util.Iterator;

import org.codehaus.jackson.map.ObjectMapper;
import org.osmtools.api.BoundingBox;
import org.osmtools.geojson.Feature;
import org.osmtools.geojson.FeatureCollection;
import org.osmtools.geojson.MultiLineString;
import org.springframework.stereotype.Service;

@Service
public class GeoJsonExport {

	private ObjectMapper objectMapper = new ObjectMapper();

	public void export(Iterable<Section> container, OutputStream os) {
		FeatureCollection featureCollection = export(container);

		try {
			objectMapper.writeValue(os, featureCollection);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public FeatureCollection export(Iterable<Section> container) {
		FeatureCollection featureCollection = new FeatureCollection();
		BoundingBox boundingBox = new BoundingBox();

		for (Iterator<Section> it = container.iterator(); it.hasNext();) {
			Section dataContainer = it.next();

			Feature feature = new Feature();
			featureCollection.addFeature(feature);
			feature.setProperty("name", dataContainer.getName());
			MultiLineString lineString = new MultiLineString();
			feature.setGeometry(lineString);

			for (Iterator<Iterable<? extends LonLat>> itC = dataContainer.getCoordinateLists().iterator(); itC
					.hasNext();) {
				Iterable<? extends LonLat> coordinates = itC.next();
				lineString.startNewLine();
				trackpointsToLineString(lineString, boundingBox, coordinates);
			}
		}
		featureCollection.setBoundingBox(boundingBox.getWest(), boundingBox.getSouth(), boundingBox.getEast(),
				boundingBox.getNorth());
		return featureCollection;
	}

	private void trackpointsToLineString(MultiLineString lineString, BoundingBox boundingBox,
			Iterable<? extends LonLat> coordinates) {
		for (Iterator<? extends LonLat> it = coordinates.iterator(); it.hasNext();) {
			LonLat point = it.next();
			lineString.addCoordinates(point.getLon(), point.getLat());
			boundingBox.addPoint(point.getLon(), point.getLat());
		}
	}

}
