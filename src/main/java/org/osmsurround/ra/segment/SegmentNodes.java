package org.osmsurround.ra.segment;

import org.osmsurround.ra.data.Node;

public class SegmentNodes {

	private Node thisNode;
	private Node otherNode;

	public SegmentNodes(Node thisNode, Node otherNode) {
		this.thisNode = thisNode;
		this.otherNode = otherNode;
	}

	public Node getThisNode() {
		return thisNode;
	}

	public Node getOtherNode() {
		return otherNode;
	}
}
