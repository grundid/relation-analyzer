package org.osmsurround.ra.analyzer;

import static org.junit.Assert.*;

import java.util.Collection;

import org.junit.Test;
import org.osmsurround.ra.TestUtils;
import org.osmsurround.ra.segment.ConnectableSegment;

public class AggregatedSegmentTest {

	@Test
	public void testGetEndpointNodes() throws Exception {
		AggregatedSegment segment = new AggregatedSegment(TestUtils.asFixedOrderWay(1, 2, 3, 4));
		Collection<ConnectableNode> endpointNodes = segment.getEndpointNodes();
		TestUtils.assertContainsNodeIds(endpointNodes, 1, 2, 3, 4);
	}

	@Test
	public void testNoAggregatedSubSegments() throws Exception {
		AggregatedSegment segment = new AggregatedSegment(TestUtils.asFlexibleOrderWay(1, 2));
		AggregatedSegment subSegment = new AggregatedSegment(TestUtils.asFlexibleOrderWay(2, 3));
		segment.addSegment(subSegment);
		for (ConnectableSegment innerSegment : segment.getSegments())
			assertFalse(innerSegment instanceof AggregatedSegment);

	}

}
