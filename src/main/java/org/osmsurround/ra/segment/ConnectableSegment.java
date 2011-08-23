package org.osmsurround.ra.segment;

import java.util.List;

import org.osmsurround.ra.analyzer.ConnectableNode;
import org.osmsurround.ra.data.Node;

public interface ConnectableSegment {

	ConnectableNode getEndpointNodes();

	ConnectableNode getStartNodes();

	ConnectableNode getEndNodes();

	List<Node> getNodesBetween(ConnectableNode startNode, ConnectableNode endNode);

	List<Node> getNodesTillEnd(ConnectableNode startNode);

	Node getCommonNode(ConnectableNode connectableNode);

	/**
	 * Returns true if the given node can connect to this segment's start or end nodes.
	 * 
	 */
	boolean canConnect(ConnectableNode node);

}
