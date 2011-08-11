package org.osmsurround.ra.analyzer;

import static org.junit.Assert.*;

import java.util.Collection;

import org.junit.Test;
import org.osmsurround.ra.TestUtils;
import org.osmsurround.ra.data.Node;

public class AggregatedSegmentTest {

	@Test
	public void testGetFirstNode() throws Exception {
		AggregatedSegment segment = new AggregatedSegment(TestUtils.asFixedOrderWay(1, 2, 3, 4));
		Collection<Node> firstNode = segment.getFirstNode();
		assertEquals(TestUtils.getNode(1), firstNode.iterator().next());
	}

	@Test
	public void testGetLastNode() throws Exception {
		AggregatedSegment segment = new AggregatedSegment(TestUtils.asFixedOrderWay(1, 2, 3, 4));
		Collection<Node> lastNode = segment.getLastNode();
		assertEquals(TestUtils.getNode(4), lastNode.iterator().next());
	}

	@Test
	public void testReverse() throws Exception {
		AggregatedSegment segment = new AggregatedSegment(TestUtils.asFlexibleOrderWay(1, 2, 3, 4));
		segment.getSegments().add(TestUtils.asFlexibleOrderWay(4, 5, 6));
		segment.reverse();
		assertEquals(TestUtils.getNode(6), segment.getFirstNode().iterator().next());
		assertEquals(TestUtils.getNode(1), segment.getLastNode().iterator().next());
	}

	@Test
	public void testIsReversible() throws Exception {
		AggregatedSegment segment = new AggregatedSegment(TestUtils.asFlexibleOrderWay(1, 2));
		segment.getSegments().add(TestUtils.asFlexibleOrderWay(2, 3));
		assertTrue(segment.isReversible());
		segment.getSegments().add(TestUtils.asFixedOrderWay(3, 4));
		assertFalse(segment.isReversible());
	}

	@Test
	public void testGetSegments() throws Exception {
		AggregatedSegment segment = new AggregatedSegment(TestUtils.asFlexibleOrderWay(1, 2));
		segment.getSegments().add(TestUtils.asFlexibleOrderWay(2, 3));
		segment.getSegments().add(TestUtils.asFixedOrderWay(3, 4));
		assertEquals(3, segment.getSegments().size());
	}
}
