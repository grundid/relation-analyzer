package org.osmsurround.ra.report;

import static org.junit.Assert.*;

import org.junit.Test;
import org.osmsurround.ra.TestBase;
import org.osmsurround.ra.TestUtils;
import org.osmsurround.ra.context.AnalyzerContext;
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
}
