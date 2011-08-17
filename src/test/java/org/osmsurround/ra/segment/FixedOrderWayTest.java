package org.osmsurround.ra.segment;

import static org.junit.Assert.*;
import static org.osmsurround.ra.TestUtils.*;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.osmsurround.ra.AnalyzerException;
import org.osmsurround.ra.analyzer.ConnectableNode;
import org.osmsurround.ra.data.Node;
import org.osmsurround.ra.data.Way;

public class FixedOrderWayTest {

	private FixedOrderWay fixedOrderWayNotReverse;
	private FixedOrderWay fixedOrderWayReverse;
	private Node firstNode;
	private Node lastNode;
	private Way way;

	@Before
	public void setup() {
		firstNode = getNode(1);
		lastNode = getNode(4);
		way = asWay(1, 2, 3, 4);
		fixedOrderWayNotReverse = new FixedOrderWay(way, false);
		fixedOrderWayReverse = new FixedOrderWay(way, true);
	}

	@Test
	public void testGetStartNodes() throws Exception {
		assertContainsNode(firstNode, fixedOrderWayNotReverse.getStartNodes());
		assertContainsNode(lastNode, fixedOrderWayReverse.getStartNodes());
	}

	@Test
	public void testGetEndNodes() throws Exception {
		assertContainsNode(lastNode, fixedOrderWayNotReverse.getEndNodes());
		assertContainsNode(firstNode, fixedOrderWayReverse.getEndNodes());
	}

	@Test
	public void testGetNodesBetweenNotReverse() throws Exception {
		Collection<Node> nodesBetweenNotReverse = fixedOrderWayNotReverse.getNodesBetween(
				new ConnectableNode(firstNode), new ConnectableNode(lastNode));
		assertNodesInOrder(new long[] { 1, 2, 3, 4 }, nodesBetweenNotReverse);
	}

	@Test
	public void testGetNodesBetweenReverse() throws Exception {
		assertNodesInOrder(new long[] { 4, 3, 2, 1 },
				fixedOrderWayReverse.getNodesBetween(new ConnectableNode(lastNode), new ConnectableNode(firstNode)));
		assertNodesInOrder(new long[] { 1, 2, 3, 4 },
				fixedOrderWayReverse.getNodesBetween(new ConnectableNode(firstNode), new ConnectableNode(lastNode)));
	}

	@Test(expected = AnalyzerException.class)
	public void testGetNodesBetweenException() throws Exception {
		fixedOrderWayNotReverse.getNodesBetween(new ConnectableNode(getNode(2)), new ConnectableNode(getNode(3)));
	}

	@Test
	public void testGetNodesTillEnd() throws Exception {
		assertNodesInOrder(new long[] { 1, 2, 3, 4 },
				fixedOrderWayNotReverse.getNodesTillEnd(new ConnectableNode(firstNode)));
		assertNodesInOrder(new long[] { 4, 3, 2, 1 },
				fixedOrderWayNotReverse.getNodesTillEnd(new ConnectableNode(lastNode)));
	}

	@Test(expected = AnalyzerException.class)
	public void testGetNodesTillEndException() throws Exception {
		fixedOrderWayNotReverse.getNodesTillEnd(new ConnectableNode(getNode(2)));
	}

	@Test
	public void testGetOppositeNode() throws Exception {
		assertOppositeNode(fixedOrderWayNotReverse, 1, 4);
		assertOppositeNode(fixedOrderWayNotReverse, 4, 1);
		assertOppositeNode(fixedOrderWayReverse, 4, 1);
		assertOppositeNode(fixedOrderWayReverse, 1, 4);
	}

	@Test
	public void testCanConnect() throws Exception {
		assertTrue(fixedOrderWayNotReverse.canConnect(new ConnectableNode(firstNode)));
		assertTrue(fixedOrderWayNotReverse.canConnect(new ConnectableNode(lastNode)));

		assertTrue(fixedOrderWayReverse.canConnect(new ConnectableNode(firstNode)));
		assertTrue(fixedOrderWayReverse.canConnect(new ConnectableNode(lastNode)));
	}

	@Test
	public void testCanConnectForwardOnly() throws Exception {
		assertTrue(fixedOrderWayNotReverse.canConnectForwardOnly(new ConnectableNode(firstNode), new ConnectableNode(
				getNode(10))));
		assertFalse(fixedOrderWayNotReverse.canConnectForwardOnly(new ConnectableNode(firstNode), new ConnectableNode(
				lastNode)));
		assertFalse(fixedOrderWayNotReverse.canConnectForwardOnly(new ConnectableNode(lastNode), new ConnectableNode(
				firstNode)));

		assertTrue(fixedOrderWayReverse.canConnectForwardOnly(new ConnectableNode(lastNode), new ConnectableNode(
				getNode(10))));
		assertFalse(fixedOrderWayReverse.canConnectForwardOnly(new ConnectableNode(lastNode), new ConnectableNode(
				firstNode)));
		assertFalse(fixedOrderWayReverse.canConnectForwardOnly(new ConnectableNode(firstNode), new ConnectableNode(
				lastNode)));

	}

}
