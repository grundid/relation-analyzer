package org.osmsurround.ra.segment;

import java.util.List;

import org.osmsurround.ra.analyzer.ConnectableNode;
import org.osmsurround.ra.data.Node;

/**
 * A segment represents a single way. Every segment has one connectable start node and one connectable end node.
 * 
 * @see ConnectableNode
 */
public interface ISegment {

	ConnectableNode getStartNodes();

	ConnectableNode getEndNodes();

	ConnectableNode getEndpointNodes();

	List<Node> getNodesBetween(ConnectableNode startNode, ConnectableNode endNode);

	List<Node> getNodesTillEnd(ConnectableNode startNode);

	Node getCommonNode(ConnectableNode connectableNode);

	/**
	 * Returns true if the given node can connect to this segment's start or end nodes.
	 * 
	 */
	boolean canConnect(ConnectableNode node);
}
