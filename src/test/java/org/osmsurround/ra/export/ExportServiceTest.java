package org.osmsurround.ra.export;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;

import org.junit.Test;
import org.osmsurround.ra.TestBase;
import org.osmsurround.ra.TestUtils;
import org.osmsurround.ra.context.AnalyzerContext;
import org.springframework.beans.factory.annotation.Autowired;

public class ExportServiceTest extends TestBase {

	@Autowired
	private ExportService exportService;

	@Test
	public void testExport() throws Exception {
		AnalyzerContext analyzerContext = helperService.createGraphContext(TestUtils.RELATION_12320_NECKARTAL_WEG);
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		exportService.export(analyzerContext, byteArrayOutputStream);
		assertTrue(byteArrayOutputStream.size() > 0);
	}
}
