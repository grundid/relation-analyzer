package org.osmsurround.ra.graph;

import java.util.Collection;
import java.util.Set;

import org.osmsurround.ra.dijkstra.Edge;

public class Graph {

	private Set<IntersectionNode> leaves;
	private Collection<Edge> edges;

	@SuppressWarnings("unchecked")
	public Graph(Set<IntersectionNode> leaves, Collection<? extends Edge> edges) {
		this.leaves = leaves;
		this.edges = (Collection<Edge>)edges;
	}

	public Set<IntersectionNode> getLeaves() {
		return leaves;
	}

	public Collection<Edge> getEdges() {
		return edges;
	}
}
