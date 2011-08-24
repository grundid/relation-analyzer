package org.osmsurround.ra.report;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.Map.Entry;

import org.junit.Test;
import org.osmsurround.ra.TestBase;
import org.osmsurround.ra.TestUtils;
import org.osmsurround.ra.context.AnalyzerContext;
import org.osmsurround.ra.data.Node;
import org.springframework.beans.factory.annotation.Autowired;

public class ReportServiceTest extends TestBase {

	@Autowired
	private ReportService reportService;

	@Test
	public void testGenerateReport() throws Exception {

		AnalyzerContext analyzerContext = helperService.createGraphContext(TestUtils.RELATION_959757_LINE_10);

		Report report = reportService.generateReport(analyzerContext);
		assertNotNull(report);
		assertNotNull(report.getRating());
		assertNotNull(report.getMessageCode());
		assertNotNull(report.getReportItems());

		assertEquals(1, report.getReportItems().size());

		ReportItem reportItem = report.getReportItems().get(0);

		assertEquals(2, reportItem.getNodes().size());

		for (Entry<Node, Collection<NodeDistance>> entry : reportItem.getNodes().entrySet()) {

			for (NodeDistance nodeDistance : entry.getValue()) {
				System.out.println(entry.getKey() + " => " + nodeDistance.getNode() + " (" + nodeDistance.getDistance()
						+ ")");
			}
		}

	}
}
