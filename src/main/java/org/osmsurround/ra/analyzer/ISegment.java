package org.osmsurround.ra.analyzer;

import java.util.Collection;

/**
 * A segment represents a single way or a group of connected and directed ways. Every segment has one or more
 * connectable start nodes and one or more connectable end nodes.
 * 
 * @see ConnectableNode
 */
public interface ISegment {

	Collection<ConnectableNode> getStartNodes();

	Collection<ConnectableNode> getEndNodes();

	//	Collection<Node> getNodesBetween(ConnectableNode startNode, ConnectableNode endNode);
}
