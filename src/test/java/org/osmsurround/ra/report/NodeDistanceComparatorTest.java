package org.osmsurround.ra.report;

import static org.junit.Assert.*;

import org.junit.Test;
import org.osmsurround.ra.data.Node;

public class NodeDistanceComparatorTest {

	@Test
	public void testCompare() throws Exception {

		NodeDistance nd1 = new NodeDistance(new Node(1, 0, 0), 10);
		NodeDistance nd2 = new NodeDistance(new Node(2, 0, 0), 20);

		NodeDistanceComparator comparator = new NodeDistanceComparator();

		assertTrue(comparator.compare(nd1, nd2) < 0);
		assertTrue(comparator.compare(nd2, nd1) > 0);
		assertTrue(comparator.compare(nd1, nd1) == 0);

	}
}
