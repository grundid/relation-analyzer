package org.osmsurround.ra.web;

import java.util.Collection;
import java.util.Set;

import org.osmsurround.ra.dijkstra.Edge;
import org.osmsurround.ra.dijkstra.Graph;

public class IntersectionWeb implements Graph {

	private Set<IntersectionNode> leaves;
	private Collection<Edge> edges;

	@SuppressWarnings("unchecked")
	public IntersectionWeb(Set<IntersectionNode> leaves, Collection<? extends Edge> edges) {
		this.leaves = leaves;
		this.edges = (Collection<Edge>)edges;
	}

	public Set<IntersectionNode> getLeaves() {
		return leaves;
	}

	@Override
	public Collection<Edge> getEdges() {
		return edges;
	}
}
