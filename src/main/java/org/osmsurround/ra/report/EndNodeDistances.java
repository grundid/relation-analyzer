package org.osmsurround.ra.report;

import java.util.Collection;

import org.osmsurround.ra.data.Node;

public class EndNodeDistances {

	private Node node;
	private Collection<NodeDistance> distances;

	public EndNodeDistances(Node node, Collection<NodeDistance> distances) {
		this.node = node;
		this.distances = distances;
	}

	public Node getNode() {
		return node;
	}

	public Collection<NodeDistance> getDistances() {
		return distances;
	}

}
