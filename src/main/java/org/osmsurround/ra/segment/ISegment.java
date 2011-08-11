package org.osmsurround.ra.segment;

import java.util.Collection;

import org.osmsurround.ra.analyzer.ConnectableNode;

/**
 * A segment represents a single way or a group of connected and directed ways. Every segment has one or more
 * connectable start nodes and one or more connectable end nodes.
 * 
 * @see ConnectableNode
 */
public interface ISegment {

	Collection<ConnectableNode> getStartNodes();

	Collection<ConnectableNode> getEndNodes();

	//	boolean containsInStartNode(ConnectableNode connectableNode);

	//	boolean containsInEndNode(ConnectableNode connectableNode);

	//	Collection<Node> getNodesBetween(ConnectableNode startNode, ConnectableNode endNode);
}
