/**
 * This file is part of Relation Analyzer for OSM.
 * Copyright (c) 2001 by Adrian Stabiszewski, as@grundid.de
 *
 * Relation Analyzer is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Relation Analyzer is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with Relation Analyzer.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.osmsurround.ra.export;

import java.io.OutputStream;
import java.math.BigDecimal;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.topografix.gpx._1._1.GpxType;
import com.topografix.gpx._1._1.ObjectFactory;
import com.topografix.gpx._1._1.TrkType;
import com.topografix.gpx._1._1.TrksegType;
import com.topografix.gpx._1._1.WptType;

@Service
public class GpxExport {

	private String appName = "relation-analyzer";
	private String appVersion = "1.1";

	@Autowired
	public GpxExport(@Value("${app.name}") String appName, @Value("${app.version}") String appVersion) {
		this.appName = appName;
		this.appVersion = appVersion;
	}

	public void export(Iterable<Section> container, OutputStream os) throws JAXBException {
		ObjectFactory of = new ObjectFactory();
		GpxType gpxType = of.createGpxType();
		gpxType.setCreator(appName);
		gpxType.setVersion(appVersion);
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
