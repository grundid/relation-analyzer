package org.osmsurround.ra.segment;

import static org.junit.Assert.*;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.osmsurround.ra.TestUtils;
import org.osmsurround.ra.analyzer.ConnectableNode;
import org.osmsurround.ra.segment.FlexibleRoundaboutWay;

public class FlexibleRoundaboutWayTest {

	private FlexibleRoundaboutWay flexibleRoundaboutWay;

	@Before
	public void setup() {
		flexibleRoundaboutWay = TestUtils.asFlexibleRoundaboutWay(10, 11, 12, 13, 10);
	}

	@Test
	public void testGetStartNodes() throws Exception {
		Collection<ConnectableNode> startNodes = flexibleRoundaboutWay.getStartNodes();
		TestUtils.assertContainsNode(TestUtils.getNode(10), startNodes);
		TestUtils.assertContainsNode(TestUtils.getNode(11), startNodes);
		TestUtils.assertContainsNode(TestUtils.getNode(12), startNodes);
		TestUtils.assertContainsNode(TestUtils.getNode(13), startNodes);
		assertEquals(5, startNodes.iterator().next().size());
	}

	@Test
	public void testGetEndNodes() throws Exception {
		Collection<ConnectableNode> endNodes = flexibleRoundaboutWay.getEndNodes();
		TestUtils.assertContainsNode(TestUtils.getNode(10), endNodes);
		TestUtils.assertContainsNode(TestUtils.getNode(11), endNodes);
		TestUtils.assertContainsNode(TestUtils.getNode(12), endNodes);
		TestUtils.assertContainsNode(TestUtils.getNode(13), endNodes);
		assertEquals(5, endNodes.iterator().next().size());
	}
}
