package org.osmsurround.ra.web;

import java.util.Collections;
import java.util.List;

import org.osmsurround.ra.data.Node;

public class Edge {

	protected IntersectionNode node1;
	private List<Node> nodes;
	protected IntersectionNode node2;

	public Edge(IntersectionNode node1, List<Node> nodes, IntersectionNode node2) {
		this.node1 = node1;
		this.nodes = nodes;
		this.node2 = node2;
	}

	public List<Node> getNodesAfterNode(Node node) {
		if (nodes.get(0).equals(node))
			return nodes.subList(1, nodes.size());
		else {
			List<Node> subNodes = nodes.subList(0, nodes.size() - 1);
			Collections.reverse(subNodes);
			return subNodes;
		}
	}

	public IntersectionNode getNextNode(IntersectionNode startingWithNode) {
		if (node1.equals(startingWithNode))
			return node2;
		else
			return node1;
	}
}
