package org.osmsurround.ra.elevation;

import java.util.ArrayList;
import java.util.List;

import org.osmsurround.ra.context.AnalyzerContext;
import org.osmsurround.ra.export.ExportService;
import org.osmsurround.ra.export.LonLat;
import org.osmsurround.ra.export.Section;
import org.osmsurround.ra.utils.LonLatMath;
import org.osmtools.srtm.SrtmService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ElevationService implements InitializingBean {

	private Logger log = LoggerFactory.getLogger(getClass());

	@Value("${srtmDataDirectory}")
	private String srtmDataDirectory;

	@Autowired
	private ExportService exportService;

	private SrtmService srtmService;

	@Override
	public void afterPropertiesSet() throws Exception {
		srtmService = new SrtmService();
		if (srtmDataDirectory != null) {
			log.info("Scanning dir for Srtm Data... " + srtmDataDirectory);
			srtmService.scanDirectory(srtmDataDirectory);
		}
	}

	public List<double[]> createElevationProfile(AnalyzerContext context) {
		List<double[]> list = new ArrayList<double[]>();
		List<Section> sections = exportService.convertToSections(context);
		if (!sections.isEmpty()) {

			Section section = sections.get(0);
			if (!section.getCoordinateLists().isEmpty()) {

				double length = 0;
				LonLat lastLonLat = null;

				for (LonLat lonLat : section.getCoordinateLists().iterator().next()) {

					double lon = lonLat.getLon();
					double lat = lonLat.getLat();

					int height = srtmService.getElevation(lon, lat);

					if (lastLonLat != null) {
						double distance = LonLatMath.distance(lastLonLat.getLon(), lastLonLat.getLat(),
								lonLat.getLon(), lonLat.getLat());
						length += distance;
						list.add(new double[] { length, height });
					}
					lastLonLat = lonLat;
				}
			}
		}
		return list;
	}
}
