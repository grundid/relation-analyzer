package org.osmsurround.ra.analyzer;

import static org.junit.Assert.*;

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
	public void testIsReverse() throws Exception {
		assertFalse(fixedOrderWayNotReverse.isReversible());
		assertFalse(fixedOrderWayReverse.isReversible());
	}

	@Test
	public void testReverse() throws Exception {
		fixedOrderWayNotReverse.reverse();
		assertEquals(nodeFirst, fixedOrderWayNotReverse.getFirstNode().iterator().next());
		fixedOrderWayReverse.reverse();
		assertEquals(nodeLast, fixedOrderWayReverse.getFirstNode().iterator().next());
	}

	@Test
	public void testGetFirstNode() throws Exception {
		assertEquals(nodeFirst, fixedOrderWayNotReverse.getFirstNode().iterator().next());
		assertEquals(nodeLast, fixedOrderWayReverse.getFirstNode().iterator().next());
	}

	@Test
	public void testGetLastNode() throws Exception {
		assertEquals(nodeLast, fixedOrderWayNotReverse.getLastNode().iterator().next());
		assertEquals(nodeFirst, fixedOrderWayReverse.getLastNode().iterator().next());
	}

	@Test
	public void testGetSegments() throws Exception {
		assertEquals(fixedOrderWayNotReverse, fixedOrderWayNotReverse.getSegments().get(0));
		assertEquals(fixedOrderWayReverse, fixedOrderWayReverse.getSegments().get(0));

	}

}
