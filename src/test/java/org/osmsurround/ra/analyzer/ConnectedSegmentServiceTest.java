package org.osmsurround.ra.analyzer;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.osmsurround.ra.AnalyzerException;
import org.osmsurround.ra.TestBase;
import org.osmsurround.ra.TestUtils;
import org.osmsurround.ra.segment.ISegment;
import org.springframework.beans.factory.annotation.Autowired;

@SuppressWarnings("unchecked")
public class ConnectedSegmentServiceTest extends TestBase {

	@Autowired
	private ConnectedSegmentService connectedSegmentService;

	@Test(expected = AnalyzerException.class)
	public void testConnectEmpty() throws Exception {
		connectedSegmentService.connect(Collections.EMPTY_LIST);
	}

	@Test
	public void testConnectSingle() throws Exception {
		List<ISegment> segments = TestUtils.asSegments(TestUtils.asNodes(1, 2));
		IntersectionNode connectedSegment = connectedSegmentService.connect(segments);
		assertNotNull(connectedSegment);

	}
}
