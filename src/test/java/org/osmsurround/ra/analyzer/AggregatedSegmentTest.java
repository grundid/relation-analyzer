package org.osmsurround.ra.analyzer;

import static org.junit.Assert.*;

import java.util.Collection;

import org.junit.Test;
import org.osmsurround.ra.TestUtils;
import org.osmsurround.ra.segment.ISegment;

public class AggregatedSegmentTest {

	@Test
	public void testGetStartNodes() throws Exception {
		AggregatedSegment segment = new AggregatedSegment(TestUtils.asFixedOrderWay(1, 2, 3, 4));
		Collection<ConnectableNode> startNodes = segment.getStartNodes();
		TestUtils.assertContainsNode(TestUtils.getNode(1), startNodes);
	}

	@Test
	public void testGetLastNode() throws Exception {
		AggregatedSegment segment = new AggregatedSegment(TestUtils.asFixedOrderWay(1, 2, 3, 4));
		Collection<ConnectableNode> endNodes = segment.getEndNodes();
		TestUtils.assertContainsNode(TestUtils.getNode(4), endNodes);
	}

	@Test
	public void testGetStartNodesFlexible() throws Exception {
		AggregatedSegment segment = new AggregatedSegment(TestUtils.asFlexibleOrderWay(1, 2, 3, 4));
		Collection<ConnectableNode> startNodes = segment.getStartNodes();
		TestUtils.assertContainsNode(TestUtils.getNode(1), startNodes);
		TestUtils.assertContainsNode(TestUtils.getNode(4), startNodes);
	}

	@Test
	public void testGetLastNodeFlexible() throws Exception {
		AggregatedSegment segment = new AggregatedSegment(TestUtils.asFlexibleOrderWay(1, 2, 3, 4));
		Collection<ConnectableNode> endNodes = segment.getEndNodes();
		TestUtils.assertContainsNode(TestUtils.getNode(1), endNodes);
		TestUtils.assertContainsNode(TestUtils.getNode(4), endNodes);
	}

	@Test
	public void testNoAggregatedSubSegments() throws Exception {
		AggregatedSegment segment = new AggregatedSegment(TestUtils.asFlexibleOrderWay(1, 2));
		AggregatedSegment subSegment = new AggregatedSegment(TestUtils.asFlexibleOrderWay(2, 3));
		segment.addSegment(subSegment);
		for (ISegment innerSegment : segment.getSegments())
			assertFalse(innerSegment instanceof AggregatedSegment);

	}

}
