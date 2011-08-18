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

	ConnectableNode getEndpointNodes();

	List<Node> getNodesBetween(ConnectableNode startNode, ConnectableNode endNode);

	List<Node> getNodesTillEnd(ConnectableNode startNode);

	Node getCommonNode(ConnectableNode connectableNode);

	/**
	 * Returns true if the given node can connect to this segment's start or end nodes.
	 * 
	 */
	boolean canConnect(ConnectableNode node);

	/**
	 * Returns true if the given node can connect to this segment's start or end nodes. Returns false if nodeToIgnore is
	 * in the start or end nodes. The purpose of this method is to find segments that can connect to a segment except
	 * the segment itself.
	 * 
	 */
	boolean canConnectExcept(ConnectableNode startNode, ConnectableNode endNodeToIgnore);
}
