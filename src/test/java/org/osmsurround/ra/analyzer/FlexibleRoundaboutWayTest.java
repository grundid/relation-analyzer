package org.osmsurround.ra.analyzer;

import static org.junit.Assert.*;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.osmsurround.ra.TestUtils;
import org.osmsurround.ra.data.Node;

public class FlexibleRoundaboutWayTest {

	private FlexibleRoundaboutWay flexibleRoundaboutWay;

	@Before
	public void setup() {
		flexibleRoundaboutWay = TestUtils.asFlexibleRoundaboutWay(10, 11, 12, 13, 10);
	}

	@Test
	public void testGetFirstNode() throws Exception {
		Collection<Node> firstNode = flexibleRoundaboutWay.getFirstNode();
		assertTrue(firstNode.contains(TestUtils.getNode(10)));
		assertTrue(firstNode.contains(TestUtils.getNode(11)));
		assertTrue(firstNode.contains(TestUtils.getNode(12)));
		assertTrue(firstNode.contains(TestUtils.getNode(13)));
		assertEquals(5, firstNode.size());
	}

	@Test
	public void testGetLastNode() throws Exception {
		Collection<Node> lastNode = flexibleRoundaboutWay.getLastNode();
		assertTrue(lastNode.contains(TestUtils.getNode(10)));
		assertTrue(lastNode.contains(TestUtils.getNode(11)));
		assertTrue(lastNode.contains(TestUtils.getNode(12)));
		assertTrue(lastNode.contains(TestUtils.getNode(13)));
		assertEquals(5, lastNode.size());
	}
}
