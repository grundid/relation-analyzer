package org.osmsurround.ra.report;

import java.util.Collection;

import org.osmsurround.ra.data.Node;

public class Segment {

	private Collection<Node> startNodes;
	private Collection<Node> endNodes;

	private double length;

	public Segment(Collection<Node> startNodes, Collection<Node> endNodes, double length) {
		this.startNodes = startNodes;
		this.endNodes = endNodes;
		this.length = length;
	}

	public Collection<Node> getStartNodes() {
		return startNodes;
	}

	public Collection<Node> getEndNodes() {
		return endNodes;
	}

	public double getLength() {
		return length;
	}

}
