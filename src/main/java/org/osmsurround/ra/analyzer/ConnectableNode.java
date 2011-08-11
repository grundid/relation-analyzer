package org.osmsurround.ra.analyzer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import org.osmsurround.ra.data.Node;

public class ConnectableNode {

	private Collection<Node> nodes;

	public ConnectableNode(Node... nodes) {
		this.nodes = Arrays.asList(nodes);
	}

	public ConnectableNode(Collection<Node> nodes) {
		this.nodes = new ArrayList<Node>(nodes);
	}

	/**
	 * Returns true, if this node can connect to the given node. This means that they have at least one common node.
	 * 
	 * @param node
	 * @return
	 */
	public boolean isConnectable(ConnectableNode node) {
		return !Collections.disjoint(nodes, node.nodes);
	}

	public boolean contains(Node node) {
		return nodes.contains(node);
	}

	public int size() {
		return nodes.size();
	}
}
