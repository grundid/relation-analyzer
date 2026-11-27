package org.osmsurround.ra.stats;

import static org.junit.Assert.*;

import org.junit.Test;
import org.osmsurround.ra.TestBase;
import org.osmsurround.ra.TestUtils;
import org.osmtools.ra.context.AnalyzerContext;
import org.springframework.beans.factory.annotation.Autowired;

public class StatisticsServiceTest extends TestBase {

	@Autowired
	private StatisticsService statisticsService;

	@Test
	public void testCreateRelationStatistics() throws Exception {

		AnalyzerContext analyzerContext = helperService.createGraphContext(TestUtils.RELATION_12320_NECKARTAL_WEG);

		RelationStatistics relationStatistics = statisticsService.createRelationStatisticsHighway(analyzerContext
				.getRelation());

		for (Distribution distribution : relationStatistics.getDistributions()) {

			//			System.out.println(distribution.getName() + " => " + distribution.getLength() + " % => "
			//					+ distribution.getPercent());

		}

		//		System.out.println("Total: " + relationStatistics.getLength());

	}

	@Test
	public void testCreateRelationStatisticsSurfaceIgnorePlatforms() throws Exception {
		AnalyzerContext analyzerContext = helperService.createGraphContext(TestUtils.RELATION_5504331_EP_EXPRESS);
		RelationStatistics relationStatistics = statisticsService.createRelationStatisticsSurface(analyzerContext
				.getRelation());
		assertEquals(2.345, relationStatistics.getLength(), 0.001);
	}

	@Test
	public void testCreateRelationStatisticsHighwayIgnorePlatforms() throws Exception {
		AnalyzerContext analyzerContext = helperService.createGraphContext(TestUtils.RELATION_2807115_TRAM_15);
		RelationStatistics relationStatistics = statisticsService.createRelationStatisticsHighway(analyzerContext
				.getRelation());
		assertEquals(0, relationStatistics.getLength(), 0.001);
	}
}
