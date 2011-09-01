package org.osmsurround.tags.builder;

import static org.junit.Assert.*;

import org.junit.Test;
import org.osmsurround.ra.data.Node;

public class PotlatchLinkBuilderTest {

	@Test
	public void testBuildLink() throws Exception {
		Node node1 = new Node(1, 49.9321f, 9.342f);
		Node node2 = new Node(2, 49.6321f, 9.542f);

		PotlatchLinkBuilder linkBuilder = new PotlatchLinkBuilder();

		assertEquals("http://www.openstreetmap.org/edit?lat=49.63209915&lon=9.54199982&zoom=18&node=2",
				linkBuilder.buildLinkForNodes(node1, node2));

	}
}
