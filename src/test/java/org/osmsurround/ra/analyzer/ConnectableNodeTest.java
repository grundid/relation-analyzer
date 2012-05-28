package org.osmsurround.ra.analyzer;

import static org.junit.Assert.*;

import org.junit.Test;
import org.osmsurround.ra.TestUtils;

public class ConnectableNodeTest {

	@Test
	public void testContains() throws Exception {
		ConnectableNode node1 = new ConnectableNode(TestUtils.getNode(1), TestUtils.getNode(2));
		assertTrue(node1.contains(TestUtils.getNode(1)));
		assertTrue(node1.contains(TestUtils.getNode(2)));
		assertFalse(node1.contains(TestUtils.getNode(3)));

		ConnectableNode node2 = new ConnectableNode(TestUtils.asNodes(1, 2, 3, 4));
		assertTrue(node2.contains(TestUtils.getNode(1)));
		assertTrue(node2.contains(TestUtils.getNode(2)));
		assertTrue(node2.contains(TestUtils.getNode(3)));
		assertTrue(node2.contains(TestUtils.getNode(4)));
		assertFalse(node2.contains(TestUtils.getNode(5)));
	}

	@Test
	public void testSize() throws Exception {
		ConnectableNode node1 = new ConnectableNode(TestUtils.getNode(1), TestUtils.getNode(2));
		assertEquals(2, node1.size());

		ConnectableNode node2 = new ConnectableNode(TestUtils.asNodes(1, 2, 3, 4));
		assertEquals(4, node2.size());
	}

	@Test
	public void testIsConnectable() throws Exception {
		ConnectableNode node1 = new ConnectableNode(TestUtils.asNodes(1, 2, 3, 4));
		ConnectableNode node2 = new ConnectableNode(TestUtils.asNodes(2));
		ConnectableNode node3 = new ConnectableNode(TestUtils.asNodes(3));

		assertTrue(node1.isConnectable(node2));
		assertTrue(node2.isConnectable(node1));
		assertTrue(node1.isConnectable(node1));
		assertTrue(node2.isConnectable(node2));

		assertFalse(node2.isConnectable(node3));
		assertFalse(node3.isConnectable(node2));

	}
}
