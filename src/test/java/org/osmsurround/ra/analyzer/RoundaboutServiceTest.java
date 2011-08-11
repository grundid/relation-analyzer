package org.osmsurround.ra.analyzer;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.osmsurround.ra.TestBase;
import org.osmsurround.ra.data.Node;
import org.osmsurround.ra.data.Way;
import org.springframework.beans.factory.annotation.Autowired;

public class RoundaboutServiceTest extends TestBase {

	@Autowired
	private RoundaboutService roundaboutService;

	@Test
	public void testIsRoundabout() throws Exception {

		Node node1 = new Node(1, 0, 0);
		Node node2 = new Node(2, 1, 1);
		Node node3 = new Node(3, 1, 2);
		Node node4 = new Node(4, 0, 0);
		Node node1bad = new Node(1, 2, 2);

		assertRoundabout(false, Arrays.asList(node1));
		assertRoundabout(false, Arrays.asList(node1, node2, node3, node4));
		assertRoundabout(false, Arrays.asList(node1, node1bad));

		assertRoundabout(true, Arrays.asList(node1, node1)); // hmm
		assertRoundabout(true, Arrays.asList(node1, node2, node1));
		assertRoundabout(true, Arrays.asList(node1, node2, node4, node1));
	}

	private void assertRoundabout(boolean expected, List<Node> nodes) {
		assertEquals(expected, roundaboutService.isRoundabout(new Way(0, nodes)));
	}
}
