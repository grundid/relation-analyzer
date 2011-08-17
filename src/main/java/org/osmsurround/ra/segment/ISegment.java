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

	ConnectableNode getOppositeNode(ConnectableNode startNode);

	//	boolean containsInStartNode(ConnectableNode connectableNode);

	//	boolean containsInEndNode(ConnectableNode connectableNode);

	List<Node> getNodesBetween(ConnectableNode startNode, ConnectableNode endNode);

	List<Node> getNodesTillEnd(ConnectableNode startNode);

	/**
	 * Returns true if the given node can connect to this segment's start or end nodes.
	 * 
	 */
	boolean canConnect(ConnectableNode node);

	/**
	 * The purpose of this method is to find out if we can go forward with this segment starting with the given node.
	 * The nodeToIgnore is a backward node that should not be connectable.
	 * 
	 */
	boolean canConnectForwardOnly(ConnectableNode startNode, ConnectableNode endNodeToIgnore);
}
