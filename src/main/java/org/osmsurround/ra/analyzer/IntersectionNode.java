package org.osmsurround.ra.analyzer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.osmsurround.ra.data.Node;

public class IntersectionNode {

	private Node node;

	private Collection<Edge> edges = new ArrayList<Edge>();

	public IntersectionNode(Node node) {
		this.node = node;
	}

	public Node getNode() {
		return node;
	}

	public void addEdge(Collection<Node> nodes, IntersectionNode endNode) {
		edges.add(new Edge(this, nodes, endNode));
	}

	public Iterator<Edge> getEdgesIterator() {
		return edges.iterator();
	}
}
