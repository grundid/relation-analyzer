package org.osmsurround.ra.report;

import static org.junit.Assert.*;

import org.junit.Test;
import org.osmsurround.ra.TestBase;
import org.osmsurround.ra.TestUtils;
import org.osmtools.ra.context.AnalyzerContext;
import org.springframework.beans.factory.annotation.Autowired;

public class ReportServiceTest extends TestBase {

	@Autowired
	private ReportService reportService;

	@Test
	public void testGenerateReport() throws Exception {

		AnalyzerContext analyzerContext = helperService.createGraphContext(TestUtils.RELATION_959757_LINE_10);

		Report report = reportService.generateReport(analyzerContext);
		assertNotNull(report);
		assertNotNull(report.getRelationRating());
		assertNotNull(report.getRelationInfo());
		assertNotNull(report.getReportItems());

		assertEquals(1, report.getReportItems().size());

		ReportItem reportItem = report.getReportItems().get(0);

		assertEquals(2, reportItem.getEndNodeDistances().size());

		assertEquals(17.732, report.getRelationInfo().getLength(), 0.001);
	}

	@Test
	public void testGenerateReportIgnorePlatforms() throws Exception {
		AnalyzerContext analyzerContext = helperService.createGraphContext(TestUtils.RELATION_5504331_EP_EXPRESS);
		Report report = reportService.generateReport(analyzerContext);
		assertEquals(1, report.getReportItems().size());
		assertEquals(2.345, report.getRelationInfo().getLength(), 0.001);
	}

	@Test
	public void testGenerateReportIgnoreHighwayPlatforms() throws Exception {
		AnalyzerContext analyzerContext = helperService.createGraphContext(TestUtils.RELATION_2807115_TRAM_15);
		Report report = reportService.generateReport(analyzerContext);
		assertEquals(1, report.getReportItems().size());
	}

	@Test
	public void testGenerateReportIgnoreConnectedPlatforms() throws Exception {
		AnalyzerContext analyzerContext = helperService.createGraphContext(TestUtils.RELATION_1903644_FERRY_F2);
		Report report = reportService.generateReport(analyzerContext);
		assertEquals(1, report.getReportItems().size());
		assertEquals(0.380, report.getRelationInfo().getLength(), 0.001);
	}
}
