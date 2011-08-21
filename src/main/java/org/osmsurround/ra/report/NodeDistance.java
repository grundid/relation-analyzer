package org.osmsurround.ra.report;

import org.osmsurround.ra.data.Node;

public class NodeDistance {

	private Node node;
	private double distance;

	public NodeDistance(Node node, double distance) {
		this.node = node;
		this.distance = distance;
	}

	public Node getNode() {
		return node;
	}

	public double getDistance() {
		return distance;
	}

}
