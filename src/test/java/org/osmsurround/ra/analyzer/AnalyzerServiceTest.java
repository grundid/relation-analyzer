package org.osmsurround.ra.analyzer;

import static org.junit.Assert.*;

import org.junit.Test;
import org.osmsurround.ra.TestBase;
import org.osmsurround.ra.TestUtils;
import org.osmsurround.ra.report.ReportService;
import org.osmtools.ra.context.AnalyzerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

public class AnalyzerServiceTest extends TestBase {

	@Autowired
	private AnalyzerService analyzerService;
	@Autowired
	private ReportService reportService;
	@Autowired
	private RestTemplate restTemplate;

	@Test
	public void testIgnorePlatforms() throws Exception {
		TestUtils.prepareRestTemplateForRelation(restTemplate, TestUtils.RELATION_5504331_EP_EXPRESS);
		AnalyzerContext analyzerContext = analyzerService.analyzeRelation(TestUtils.RELATION_5504331_EP_EXPRESS, false);

		assertEquals(10, analyzerContext.getSegments().size());
		assertEquals(1, analyzerContext.getAggregatedSegments().size());
	}

	@Test
	public void testIgnoreStopWayKeepForwardBackward() throws Exception {
		TestUtils.prepareRestTemplateForRelation(restTemplate, TestUtils.RELATION_73714_BUS);
		AnalyzerContext analyzerContext = analyzerService.analyzeRelation(TestUtils.RELATION_73714_BUS, false);

		assertEquals(226, analyzerContext.getSegments().size());
		assertEquals(1, reportService.generateReport(analyzerContext).getReportItems().size());
	}
}
