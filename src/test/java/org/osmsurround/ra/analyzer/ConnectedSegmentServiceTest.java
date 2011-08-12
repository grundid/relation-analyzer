package org.osmsurround.ra.analyzer;

import java.util.Collections;

import org.junit.Test;
import org.osmsurround.ra.AnalyzerException;
import org.osmsurround.ra.TestBase;
import org.springframework.beans.factory.annotation.Autowired;

@SuppressWarnings("unchecked")
public class ConnectedSegmentServiceTest extends TestBase {

	@Autowired
	private ConnectedSegmentService connectedSegmentService;

	@Test(expected = AnalyzerException.class)
	public void testConnectEmpty() throws Exception {
		connectedSegmentService.connect(Collections.EMPTY_LIST);
	}
	//
	//	@Test
	//	public void testConnectSingle() throws Exception {
	//		List<ISegment> segments = TestUtils.asSegments(TestUtils.asNodes(1, 2));
	//		IntersectionNode connectedSegment = connectedSegmentService.connect(segments);
	//		assertNotNull(connectedSegment);
	//
	//	}
}
