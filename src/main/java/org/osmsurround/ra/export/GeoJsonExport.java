package org.osmsurround.ra.export;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;

import org.springframework.stereotype.Service;

@Service
public class GeoJsonExport {

	public void export(Iterable<Section> container, OutputStream os) {
		StringBuilder json = new StringBuilder();
		json.append("{\"type\":\"FeatureCollection\",\"features\":[");
		for (Iterator<Section> it = container.iterator(); it.hasNext();) {
			Section dataContainer = it.next();
			json.append("{\"type\":\"Feature\",\"geometry\":{");
			json.append("{\"type\":\"LineString\",");
			json.append("\"properties\":{\"name:\":\"").append(dataContainer.getName()).append("\"},");
			json.append("\"coordinates\":[");

			for (Iterator<Iterable<? extends LonLat>> itC = dataContainer.getCoordinateLists().iterator(); itC
					.hasNext();) {
				Iterable<? extends LonLat> coordinates = itC.next();
				addTrackpoints(json, coordinates);
				if (itC.hasNext())
					json.append(",");
			}
			json.append("]}}}");
			if (it.hasNext())
				json.append(",");
		}

		json.append("]}");

		try {
			os.write(json.toString().getBytes());
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private void addTrackpoints(StringBuilder sb, Iterable<? extends LonLat> coordinates) {
		for (Iterator<? extends LonLat> it = coordinates.iterator(); it.hasNext();) {
			LonLat point = it.next();
			sb.append("[").append(point.getLon()).append(",").append(point.getLat()).append("]");
			if (it.hasNext())
				sb.append(",");
		}
	}

}
