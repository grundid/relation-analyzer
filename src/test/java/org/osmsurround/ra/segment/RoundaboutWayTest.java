package org.osmsurround.ra.segment;

import static org.junit.Assert.*;
import static org.osmsurround.ra.TestUtils.*;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.osmsurround.ra.analyzer.ConnectableNode;
import org.osmsurround.ra.data.Node;

public class RoundaboutWayTest {

	private RoundaboutWay roundaboutWay;

	@Before
	public void setup() {
		roundaboutWay = asRoundaboutWay(1, 2, 3, 4, 5, 1);
	}

	@Test
	public void testGetStartNodes() throws Exception {
		ConnectableNode startNodes = roundaboutWay.getStartNodes();
		assertContainsNode(getNode(1), startNodes);
		assertContainsNode(getNode(2), startNodes);
		assertContainsNode(getNode(3), startNodes);
		assertContainsNode(getNode(4), startNodes);
		assertContainsNode(getNode(5), startNodes);
		assertEquals(6, startNodes.size());
	}

	@Test
	public void testGetEndNodes() throws Exception {
		ConnectableNode endNodes = roundaboutWay.getEndNodes();
		assertContainsNode(getNode(1), endNodes);
		assertContainsNode(getNode(2), endNodes);
		assertContainsNode(getNode(3), endNodes);
		assertContainsNode(getNode(4), endNodes);
		assertContainsNode(getNode(5), endNodes);
		assertEquals(6, endNodes.size());
	}

	@Test
	public void testGetNodesBetween() throws Exception {
		Collection<Node> nodesBetweenNotReverse = roundaboutWay.getNodesBetween(new ConnectableNode(getNode(1)),
				new ConnectableNode(getNode(4)));
		assertNodesInOrder(new long[] { 1, 2, 3, 4 }, nodesBetweenNotReverse);
	}

	@Test
	public void testGetNodesBetweenReverse() throws Exception {
		Collection<Node> nodesBetweenReverse = roundaboutWay.getNodesBetween(new ConnectableNode(getNode(4)),
				new ConnectableNode(getNode(1)));
		assertNodesInOrder(new long[] { 4, 5, 1 }, nodesBetweenReverse);
	}

	@Test
	public void testGetNodesTillEnd() throws Exception {
		Collection<Node> nodes1 = roundaboutWay.getNodesTillEnd(new ConnectableNode(getNode(1)));
		assertNodesInOrder(new long[] { 1, 2, 3, 4, 5, 1 }, nodes1);

		Collection<Node> nodes2 = roundaboutWay.getNodesTillEnd(new ConnectableNode(getNode(4)));
		assertNodesInOrder(new long[] { 4, 5, 1, 2, 3, 4 }, nodes2);
	}

	@Test
	public void testCanConnect() throws Exception {
		for (int x = 1; x < 6; x++)
			assertTrue(roundaboutWay.canConnect(new ConnectableNode(getNode(x))));
	}

	@Test
	public void testGetEndPointNodes() throws Exception {
		ConnectableNode endpointNodes = roundaboutWay.getEndpointNodes();
		assertContainsNode(getNode(1), endpointNodes);
		assertContainsNode(getNode(2), endpointNodes);
		assertContainsNode(getNode(3), endpointNodes);
		assertContainsNode(getNode(4), endpointNodes);
		assertContainsNode(getNode(5), endpointNodes);
		assertEquals(6, endpointNodes.size());
	}

	@Test
	public void testGetCommonNode() throws Exception {
		RoundaboutWay myRoundaboutWay = asRoundaboutWay(6, 7, 8, 5, 6);
		Node commonNode = roundaboutWay.getCommonNode(myRoundaboutWay.getEndpointNodes());
		assertEquals(getNode(5), commonNode);
	}

}
