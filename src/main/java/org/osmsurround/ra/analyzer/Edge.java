package org.osmsurround.ra.analyzer;

import java.util.Collection;

import org.osmsurround.ra.data.Node;

public class Edge {

	private IntersectionNode node1;
	private Collection<Node> nodes;
	private IntersectionNode node2;

	public Edge(IntersectionNode node1, Collection<Node> nodes, IntersectionNode node2) {
		this.node1 = node1;
		this.nodes = nodes;
		this.node2 = node2;
	}

	public Collection<Node> getNodes() {
		return nodes;
	}

	public IntersectionNode getNextNode(IntersectionNode startingWithNode) {
		if (node1.equals(startingWithNode))
			return node2;
		else
			return node1;
	}
}
