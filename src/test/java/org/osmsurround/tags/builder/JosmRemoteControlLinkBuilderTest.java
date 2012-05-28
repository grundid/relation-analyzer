package org.osmsurround.tags.builder;

import static org.junit.Assert.*;

import org.junit.Test;
import org.osmsurround.ra.data.Node;

public class JosmRemoteControlLinkBuilderTest {

	@Test
	public void testBuildLink() throws Exception {
		Node node1 = new Node(1, 49.9321f, 9.342f);
		Node node2 = new Node(2, 49.6321f, 9.542f);

		JosmRemoteControlLinkBuilder linkBuilder = new JosmRemoteControlLinkBuilder();

		assertEquals("http://localhost:8111/load_and_zoom?left=9.33699989&right=9.54699993&"
				+ "top=49.93709946&bottom=49.62709808&select=node1,node2", linkBuilder.buildLinkForNodes(node1, node2));

	}
}
