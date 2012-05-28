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
	public void testExportGpx() throws Exception {
		AnalyzerContext analyzerContext = helperService.createGraphContext(TestUtils.RELATION_12320_NECKARTAL_WEG);
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		exportService.export(analyzerContext, byteArrayOutputStream, "gpx");
		assertTrue(byteArrayOutputStream.size() > 0);
	}

	@Test
	public void testExportJson() throws Exception {
		AnalyzerContext analyzerContext = helperService.createGraphContext(TestUtils.RELATION_12320_NECKARTAL_WEG);
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		exportService.export(analyzerContext, byteArrayOutputStream, "json");
		assertTrue(byteArrayOutputStream.size() > 0);
	}
}
