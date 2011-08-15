package org.osmsurround.ra.segment;

import static org.osmsurround.ra.TestUtils.*;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.osmsurround.ra.TestUtils;
import org.osmsurround.ra.analyzer.ConnectableNode;
import org.osmsurround.ra.data.Node;

public class FlexibleOrderWayTest {

	private Node firstNode;
	private Node lastNode;
	private FlexibleOrderWay flexibleOrderWay;

	@Before
	public void setup() {
		firstNode = getNode(1);
		lastNode = getNode(4);
		flexibleOrderWay = new FlexibleOrderWay(TestUtils.asWay(1, 2, 3, 4));
	}

	@Test
	public void testGetNodesBetween() throws Exception {
		Collection<Node> nodesBetweenNotReverse = flexibleOrderWay.getNodesBetween(new ConnectableNode(firstNode),
				new ConnectableNode(lastNode));
		assertNodesInOrder(new long[] { 1, 2, 3, 4 }, nodesBetweenNotReverse);
	}

	@Test
	public void testGetNodesBetweenReverse() throws Exception {
		Collection<Node> nodesBetweenReverse = flexibleOrderWay.getNodesBetween(new ConnectableNode(lastNode),
				new ConnectableNode(firstNode));
		assertNodesInOrder(new long[] { 4, 3, 2, 1 }, nodesBetweenReverse);
	}

	@Test
	public void testGetNodesTillEnd() throws Exception {
		assertNodesInOrder(new long[] { 1, 2, 3, 4 }, flexibleOrderWay.getNodesTillEnd(new ConnectableNode(firstNode)));
		assertNodesInOrder(new long[] { 4, 3, 2, 1 }, flexibleOrderWay.getNodesTillEnd(new ConnectableNode(lastNode)));
	}

	@Test
	public void testGetOppositeNode() throws Exception {
		assertOppositeNode(flexibleOrderWay, 1, 4);
		assertOppositeNode(flexibleOrderWay, 4, 1);
	}

}
