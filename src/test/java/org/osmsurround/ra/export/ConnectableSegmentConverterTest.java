package org.osmsurround.ra.export;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.osmsurround.ra.TestBase;
import org.osmsurround.ra.TestUtils;
import org.osmsurround.ra.context.AnalyzerContext;

public class ConnectableSegmentConverterTest extends TestBase {

	@Test
	public void testConvert() throws Exception {
		AnalyzerContext analyzerContext = helperService.createGraphContext(TestUtils.RELATION_12320_NECKARTAL_WEG);
		SimpleSegmentConverter converter = new SimpleSegmentConverter();
		List<Section> sectionList = converter.convert(analyzerContext);
		assertNotNull(sectionList);
		assertEquals(1, sectionList.size());
	}
}
