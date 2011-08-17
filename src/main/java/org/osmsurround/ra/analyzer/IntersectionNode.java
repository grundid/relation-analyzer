package org.osmsurround.ra.analyzer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.osmsurround.ra.data.Node;

public class IntersectionNode {

	private Node node;

	private List<Edge> edges = new ArrayList<Edge>();

	public IntersectionNode(Node node) {
		this.node = node;
	}

	public Node getNode() {
		return node;
	}

	public void addEdge(List<Node> nodes, IntersectionNode endNode) {
		edges.add(new Edge(this, nodes, endNode));
	}

	public Iterator<Edge> getEdgesIterator() {
		return edges.iterator();
	}

	//	public List<Edge> getEdgesStartingWithThis()
	//	{
	//		List<Edge> newEdges 
	//	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((node == null) ? 0 : node.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IntersectionNode other = (IntersectionNode)obj;
		if (node == null) {
			if (other.node != null)
				return false;
		}
		else if (!node.equals(other.node))
			return false;
		return true;
	}

}
