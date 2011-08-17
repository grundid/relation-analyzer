package org.osmsurround.ra.export;

import static org.junit.Assert.*;
import static org.osmsurround.ra.TestUtils.*;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import org.osmsurround.ra.SegmentsBuilder;
import org.osmsurround.ra.TestBase;
import org.osmsurround.ra.analyzer.IntersectionNode;
import org.osmsurround.ra.data.Node;

public class TraverserTest extends TestBase {

	@Test
	public void testTraverseOrder() throws Exception {

		Collection<IntersectionNode> leaves = executeAndGetLeaves(SegmentsBuilder.create().appendFlexible(4, 5)
				.appendFlexible(4, 3).appendFlexible(3, 2).appendFlexible(2, 1));

		Iterator<IntersectionNode> it = leaves.iterator();

		IntersectionNode node1 = it.next();
		IntersectionNode node2 = it.next();

		Traverser traverser = new Traverser(node1, node2);
		List<Node> nodes = traverser.getNodes();

		assertEquals(getNode(5), nodes.get(0));
		assertEquals(getNode(4), nodes.get(1));
		assertEquals(getNode(3), nodes.get(2));
		assertEquals(getNode(2), nodes.get(3));
		assertEquals(getNode(1), nodes.get(4));

	}

}
