package org.osmsurround.ra.segment;

import java.util.Collection;

import org.osmsurround.ra.analyzer.ConnectableNode;
import org.osmsurround.ra.data.Node;

public interface ConnectableSegment {

	SegmentNodes getSegmentNodes();

	ConnectableNode getEndpointNodes();

	Node getCommonNode(ConnectableSegment otherSegment);

	/**
	 * Returns true if the given node can connect to this segment's start or end nodes.
	 * 
	 */
	boolean canConnect(ConnectableSegment otherSegment);

	boolean canConnectNodesInDirection(Node startNode, Node endNode);

	void appendNodesBetween(Collection<Node> nodes, Node startNode, Node endNode);

	boolean containsNodes(Node... nodes);
}
