package org.osmsurround.ra.export;

import org.codehaus.jackson.map.ObjectMapper;
import org.geojson.Feature;
import org.geojson.FeatureCollection;
import org.geojson.LngLatAlt;
import org.geojson.MultiLineString;
import org.osmtools.api.LonLat;
import org.osmtools.api.Section;
import org.osmtools.ra.data.Relation;
import org.springframework.stereotype.Service;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class GeoJsonExport {

    private ObjectMapper objectMapper = new ObjectMapper();

    public void export(Iterable<Section> container, Relation relation, OutputStream os) {
        FeatureCollection featureCollection = export(container, relation);
        try {
            objectMapper.writeValue(os, featureCollection);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public FeatureCollection export(Iterable<Section> container, Relation relation) {
        FeatureCollection featureCollection = new FeatureCollection();
        BoundingBox boundingBox = new BoundingBox();
        for (Iterator<Section> it = container.iterator(); it.hasNext(); ) {
            Section dataContainer = it.next();
            Feature feature = new Feature();
            featureCollection.add(feature);
            feature.setProperty("name", dataContainer.getName());
            feature.getProperties().putAll(relation.getTags());
            MultiLineString lineString = new MultiLineString();
            feature.setGeometry(lineString);
            for (Iterator<Iterable<? extends LonLat>> itC = dataContainer.getCoordinateLists().iterator(); itC
                    .hasNext(); ) {
                Iterable<? extends LonLat> coordinates = itC.next();
                trackpointsToLineString(lineString, boundingBox, coordinates);
            }
        }
        featureCollection.setBbox(boundingBox.toArray());
        return featureCollection;
    }

    private void trackpointsToLineString(MultiLineString lineString, BoundingBox boundingBox,
            Iterable<? extends LonLat> coordinates) {
        List<LngLatAlt> points = new ArrayList<LngLatAlt>();
        for (Iterator<? extends LonLat> it = coordinates.iterator(); it.hasNext(); ) {
            LonLat point = it.next();
            LngLatAlt lngLat = new LngLatAlt(point.getLon(), point.getLat());
            points.add(lngLat);
            boundingBox.addPoint(point.getLon(), point.getLat());
        }
        lineString.add(points);
    }
}
