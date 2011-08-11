package org.osmsurround.ra.analyzer;

import org.junit.Before;
import org.junit.Test;
import org.osmsurround.ra.TestUtils;
import org.osmsurround.ra.data.Node;
import org.osmsurround.ra.data.Way;

public class FixedOrderWayTest {

	private FixedOrderWay fixedOrderWayNotReverse;
	private FixedOrderWay fixedOrderWayReverse;
	private Node nodeFirst;
	private Node nodeLast;
	private Way way;

	@Before
	public void setup() {
		nodeFirst = TestUtils.getNode(1);
		nodeLast = TestUtils.getNode(4);

		way = new Way(0, TestUtils.asNodes(1, 2, 3, 4));

		fixedOrderWayNotReverse = new FixedOrderWay(way, false);
		fixedOrderWayReverse = new FixedOrderWay(way, true);
	}

	@Test
	public void testGetStartNodes() throws Exception {
		TestUtils.assertContainsNode(nodeFirst, fixedOrderWayNotReverse.getStartNodes());
		TestUtils.assertContainsNode(nodeLast, fixedOrderWayReverse.getStartNodes());
	}

	@Test
	public void testGetEndNodes() throws Exception {
		TestUtils.assertContainsNode(nodeLast, fixedOrderWayNotReverse.getEndNodes());
		TestUtils.assertContainsNode(nodeFirst, fixedOrderWayReverse.getEndNodes());
	}
}
