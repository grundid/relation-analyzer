package org.osmsurround.ra.report;

import org.junit.Ignore;
import org.junit.Test;
import org.osmsurround.ra.HelperService;
import org.osmsurround.ra.TestBase;
import org.springframework.beans.factory.annotation.Autowired;

public class AnalyzerReportServiceTest extends TestBase {

	@Autowired
	private AnalyzerReportService analyzerReportService;
	@Autowired
	private HelperService helperService;

	@Test
	@Ignore
	public void testCreateReport() throws Exception {

		//		Map<String, List<ISegment>> aggregatedRelation = helperService
		//				.loadSplittedAndAggregatedRelation(TestUtils.RELATION_12320);
		//
		//		AnalyzerReport analyzerReport = analyzerReportService.createReport(aggregatedRelation);
		//		assertNotNull(analyzerReport);
		//		assertNotNull(analyzerReport.getReportItems());
		//
		//		AnalyzerReportItem reportItem = analyzerReport.getReportItems().get(0);
		//		assertEquals("", reportItem.getRole());
		//		assertEquals(1, reportItem.getRelationItems().size());

	}
}
