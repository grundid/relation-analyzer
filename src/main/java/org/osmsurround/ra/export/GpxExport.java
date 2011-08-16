package org.osmsurround.ra.export;

import java.io.OutputStream;
import java.math.BigDecimal;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.springframework.stereotype.Service;

import com.topografix.gpx._1._1.GpxType;
import com.topografix.gpx._1._1.ObjectFactory;
import com.topografix.gpx._1._1.TrkType;
import com.topografix.gpx._1._1.TrksegType;
import com.topografix.gpx._1._1.WptType;

@Service
public class GpxExport {

	public void export(Iterable<Section> container, OutputStream os) throws JAXBException {
		ObjectFactory of = new ObjectFactory();
		GpxType gpxType = of.createGpxType();
		gpxType.setCreator("relation-analyzer");
		gpxType.setVersion("1.1");
		for (Section dataContainer : container) {
			TrkType trkType = of.createTrkType();
			gpxType.getTrk().add(trkType);
			trkType.setName(dataContainer.getName());

			TrksegType trksegType = of.createTrksegType();
			trkType.getTrkseg().add(trksegType);

			for (LonLat point : dataContainer.getCoordinates()) {
				WptType wptType = of.createWptType();
				wptType.setLat(new BigDecimal(point.getLat()));
				wptType.setLon(new BigDecimal(point.getLon()));
				trksegType.getTrkpt().add(wptType);
			}
		}
		JAXBContext jaxbContext = JAXBContext.newInstance(GpxType.class);
		Marshaller marshaller = jaxbContext.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		marshaller.marshal(of.createGpx(gpxType), os);
	}

}
