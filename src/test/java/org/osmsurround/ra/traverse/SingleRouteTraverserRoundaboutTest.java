package org.osmsurround.ra.traverse;

import static org.junit.Assert.*;
import static org.osmsurround.ra.TestUtils.*;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.osmsurround.ra.SegmentsBuilder;
import org.osmsurround.ra.TestBase;
import org.osmsurround.ra.data.Node;
import org.osmsurround.ra.graph.IntersectionNode;

public class SingleRouteTraverserRoundaboutTest extends TestBase {

	private Collection<IntersectionNode> leaves;
	private IntersectionNode node;

	@Before
	public void setup() {
		leaves = executeAndGetLeaves(SegmentsBuilder.create().appendFlexible(5, 4).appendFlexible(4, 3)
				.appendFlexible(3, 2).appendFlexible(2, 1).appendFlexible(5, 1));

		Iterator<IntersectionNode> it = leaves.iterator();

		node = it.next();

	}

	@Test
	public void testTraverseRoundabout() throws Exception {

		SingleRouteTraverser traverser = new SingleRouteTraverser(node, node);
		List<Node> nodes = traverser.getPath();

		assertEquals(getNode(5), nodes.get(0));
		assertEquals(getNode(4), nodes.get(1));
		assertEquals(getNode(3), nodes.get(2));
		assertEquals(getNode(2), nodes.get(3));
		assertEquals(getNode(1), nodes.get(4));
		assertEquals(getNode(5), nodes.get(5));
	}
}
