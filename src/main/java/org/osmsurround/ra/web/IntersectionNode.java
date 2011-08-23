package org.osmsurround.ra.web;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.osmsurround.ra.data.Node;
import org.osmsurround.ra.dijkstra.Vertex;

public class IntersectionNode implements Vertex {

	private Node node;

	private List<IntersectionNode> edges = new ArrayList<IntersectionNode>();

	public IntersectionNode(Node node) {
		this.node = node;
	}

	@Override
	public Node getNode() {
		return node;
	}

	public void addEdge(IntersectionNode endNode) {
		edges.add(endNode);
	}

	public Iterator<IntersectionNode> getEdgesIterator() {
		return edges.iterator();
	}

	public boolean isLeaf() {
		return edges.size() == 1;
	}

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
