package org.osmsurround.ra.segment;

import static org.junit.Assert.*;
import static org.osmsurround.ra.TestUtils.*;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.osmsurround.ra.AnalyzerException;
import org.osmsurround.ra.analyzer.ConnectableNode;
import org.osmsurround.ra.data.Node;

public class FlexibleWayTest {

	private FlexibleWay flexibleWay;
	private Node firstNode;
	private Node lastNode;

	@Before
	public void setup() {
		firstNode = getNode(1);
		lastNode = getNode(4);
		flexibleWay = asFlexibleOrderWay(1, 2, 3, 4);
	}

	private void assertCanConnect(boolean expected, long... otherWayNodeIds) {
		assertTrue(expected == flexibleWay.canConnect(asFlexibleOrderWay(otherWayNodeIds)));
	}

	@Test
	public void testGetEndPointNodes() throws Exception {
		ConnectableNode endpointNodesReverse = flexibleWay.getEndpointNodes();
		assertTrue(endpointNodesReverse.contains(getNode(1)));
		assertTrue(endpointNodesReverse.contains(getNode(2)));
		assertTrue(endpointNodesReverse.contains(getNode(3)));
		assertTrue(endpointNodesReverse.contains(getNode(4)));
	}

	@Test
	public void testGetSegmentNodes() throws Exception {
		SegmentNodes segmentNodes = flexibleWay.getSegmentNodes();
		assertEquals(firstNode, segmentNodes.getThisNode());
		assertEquals(lastNode, segmentNodes.getOtherNode());
	}

	@Test
	public void testGetCommonNode() throws Exception {
		assertCommonNode(4, 6, 4, 7);
		assertCommonNode(1, 1, 6, 7);
		assertCommonNode(4, 4, 6, 7);
		assertCommonNode(4, 5, 4, 7);
		assertCommonNode(3, 5, 3, 7);
		assertCommonNode(3, 3, 8, 7);
	}

	private void assertCommonNode(long expectedNodeId, long... otherWayNodeIds) {
		Set<Node> commonNodes = flexibleWay.getCommonNode(asFlexibleOrderWay(otherWayNodeIds));
		assertTrue(commonNodes.contains(getNode(expectedNodeId)));
	}

	@Test(expected = AnalyzerException.class)
	public void testGetCommonNodeFail() throws Exception {
		flexibleWay.getCommonNode(asFlexibleOrderWay(7, 8, 9));
	}

	@Test
	public void testCanConnect() throws Exception {
		assertCanConnect(true, 6, 4, 7);
		assertCanConnect(true, 1, 6, 7);
		assertCanConnect(true, 4, 6, 7);
		assertCanConnect(true, 5, 4, 7);
		assertCanConnect(true, 5, 3, 7);
		assertCanConnect(false, 7, 8, 9);
	}

	@Test
	public void testAppendNodesBetween() throws Exception {
		assertNodesInOrder(flexibleWay, firstNode, lastNode, 2, 3, 4);
		assertNodesInOrder(flexibleWay, lastNode, firstNode, 3, 2, 1);
	}

	@Test
	public void testContainsNodes() throws Exception {
		assertTrue(flexibleWay.containsNodes(getNode(1), getNode(2), getNode(3), getNode(4)));
		assertFalse(flexibleWay.containsNodes(getNode(5), getNode(6), getNode(7), getNode(8)));
	}

	@Test
	public void testCanConnectNodesInDirection() throws Exception {
		assertTrue(flexibleWay.canConnectNodesInDirection(firstNode, lastNode));
		assertTrue(flexibleWay.canConnectNodesInDirection(lastNode, firstNode));
	}

}
