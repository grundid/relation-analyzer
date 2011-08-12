package org.osmsurround.ra.segment;

import static org.junit.Assert.*;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.osmsurround.ra.TestUtils;
import org.osmsurround.ra.analyzer.ConnectableNode;
import org.osmsurround.ra.data.Node;

public class RoundaboutWayTest {

	private RoundaboutWay flexibleRoundaboutWay;

	@Before
	public void setup() {
		flexibleRoundaboutWay = TestUtils.asFlexibleRoundaboutWay(1, 2, 3, 4, 5, 1);
	}

	@Test
	public void testGetStartNodes() throws Exception {
		ConnectableNode startNodes = flexibleRoundaboutWay.getStartNodes();
		TestUtils.assertContainsNode(TestUtils.getNode(1), startNodes);
		TestUtils.assertContainsNode(TestUtils.getNode(2), startNodes);
		TestUtils.assertContainsNode(TestUtils.getNode(3), startNodes);
		TestUtils.assertContainsNode(TestUtils.getNode(4), startNodes);
		TestUtils.assertContainsNode(TestUtils.getNode(5), startNodes);
		assertEquals(6, startNodes.size());
	}

	@Test
	public void testGetEndNodes() throws Exception {
		ConnectableNode endNodes = flexibleRoundaboutWay.getEndNodes();
		TestUtils.assertContainsNode(TestUtils.getNode(1), endNodes);
		TestUtils.assertContainsNode(TestUtils.getNode(2), endNodes);
		TestUtils.assertContainsNode(TestUtils.getNode(3), endNodes);
		TestUtils.assertContainsNode(TestUtils.getNode(4), endNodes);
		TestUtils.assertContainsNode(TestUtils.getNode(5), endNodes);
		assertEquals(6, endNodes.size());
	}

	@Test
	public void testGetNodesBetween() throws Exception {
		Collection<Node> nodesBetweenNotReverse = flexibleRoundaboutWay.getNodesBetween(
				new ConnectableNode(TestUtils.getNode(1)), new ConnectableNode(TestUtils.getNode(4)));
		TestUtils.assertNodesInOrder(new long[] { 1, 2, 3, 4 }, nodesBetweenNotReverse);
	}

	@Test
	public void testGetNodesBetweenReverse() throws Exception {
		Collection<Node> nodesBetweenReverse = flexibleRoundaboutWay.getNodesBetween(
				new ConnectableNode(TestUtils.getNode(4)), new ConnectableNode(TestUtils.getNode(1)));
		TestUtils.assertNodesInOrder(new long[] { 4, 5, 1 }, nodesBetweenReverse);
	}

	@Test
	public void testGetNodesTillEnd() throws Exception {
		Collection<Node> nodes1 = flexibleRoundaboutWay.getNodesTillEnd(new ConnectableNode(TestUtils.getNode(1)));
		TestUtils.assertNodesInOrder(new long[] { 1, 2, 3, 4, 5, 1 }, nodes1);

		Collection<Node> nodes2 = flexibleRoundaboutWay.getNodesTillEnd(new ConnectableNode(TestUtils.getNode(4)));
		TestUtils.assertNodesInOrder(new long[] { 4, 5, 1, 2, 3, 4 }, nodes2);
	}

}
