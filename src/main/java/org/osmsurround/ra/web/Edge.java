package org.osmsurround.ra.web;

import org.osmsurround.ra.dijkstra.Vertex;

public class Edge implements org.osmsurround.ra.dijkstra.Edge {

	private IntersectionNode source;
	private IntersectionNode destination;

	public Edge(IntersectionNode source, IntersectionNode destination) {
		this.source = source;
		this.destination = destination;
	}

	@Override
	public Vertex getSource() {
		return source;
	}

	@Override
	public Vertex getDestination() {
		return destination;
	}

	@Override
	public int getWeight() {
		return 0;
	}
}
