package org.osmsurround.ra.traverse;

import static org.junit.Assert.*;
import static org.osmsurround.ra.TestUtils.*;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import org.osmsurround.ra.SegmentsBuilder;
import org.osmsurround.ra.TestBase;
import org.osmsurround.ra.data.Node;
import org.osmsurround.ra.graph.IntersectionNode;
import org.osmsurround.ra.traverse.SingleRouteTraverser;

public class SingleRouteTraverserTest extends TestBase {

	@Test
	public void testTraverseOrderAtoB() throws Exception {

		Collection<IntersectionNode> leaves = executeAndGetLeaves(SegmentsBuilder.create().appendFlexible(4, 5)
				.appendFlexible(4, 3).appendFlexible(3, 2).appendFlexible(2, 1));

		Iterator<IntersectionNode> it = leaves.iterator();

		IntersectionNode nodeA = it.next();
		IntersectionNode nodeB = it.next();

		SingleRouteTraverser traverser = new SingleRouteTraverser(nodeA, nodeB);
		List<Node> nodes = traverser.getPath();

		assertEquals(getNode(1), nodes.get(0));
		assertEquals(getNode(2), nodes.get(1));
		assertEquals(getNode(3), nodes.get(2));
		assertEquals(getNode(4), nodes.get(3));
		assertEquals(getNode(5), nodes.get(4));
	}

	@Test
	public void testTraverseOrderBtoA() throws Exception {

		Collection<IntersectionNode> leaves = executeAndGetLeaves(SegmentsBuilder.create().appendFlexible(4, 5)
				.appendFlexible(4, 3).appendFlexible(3, 2).appendFlexible(2, 1));

		Iterator<IntersectionNode> it = leaves.iterator();

		IntersectionNode nodeA = it.next();
		IntersectionNode nodeB = it.next();

		SingleRouteTraverser traverser = new SingleRouteTraverser(nodeB, nodeA);
		List<Node> nodes = traverser.getPath();

		assertEquals(getNode(5), nodes.get(0));
		assertEquals(getNode(4), nodes.get(1));
		assertEquals(getNode(3), nodes.get(2));
		assertEquals(getNode(2), nodes.get(3));
		assertEquals(getNode(1), nodes.get(4));
	}
}
