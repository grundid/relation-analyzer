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
}
