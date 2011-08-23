package org.osmsurround.ra.segment;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.osmsurround.ra.AnalyzerException;
import org.osmsurround.ra.analyzer.ConnectableNode;
import org.osmsurround.ra.data.Node;

public class FlexibleWay implements ConnectableSegment {

	protected List<Node> wayNodes;
	private ConnectableNode endpointNodes;

	public FlexibleWay(List<Node> nodes) {
		this.wayNodes = nodes;
		this.endpointNodes = new ConnectableNode(nodes);
	}

	@Override
	public SegmentNodes getSegmentNodes() {
		return new SegmentNodes(wayNodes.get(0), wayNodes.get(wayNodes.size() - 1));
	}

	@Override
	public ConnectableNode getEndpointNodes() {
		return endpointNodes;
	}

	@Override
	public Node getCommonNode(ConnectableSegment otherSegment) {
		Node commonNode = getCommonNodeInternal(otherSegment);
		if (commonNode == null)
			throw new AnalyzerException("No common nodes");

		return commonNode;
	}

	@Override
	public boolean canConnect(ConnectableSegment otherSegment) {
		Node commonNode = getCommonNodeInternal(otherSegment);
		return commonNode != null;
	}

	private Node getCommonNodeInternal(ConnectableSegment otherSegment) {
		for (Iterator<Node> it = otherSegment.getEndpointNodes().getNodesIterator(); it.hasNext();) {
			Node externalNode = it.next();
			if (wayNodes.contains(externalNode))
				return externalNode;
		}
		return null;
	}

	@Override
	public void appendNodesBetween(Collection<Node> nodes, Node startNode, Node endNode) {

		int startNodeIndex = wayNodes.indexOf(startNode);
		int endNodeIndex = wayNodes.indexOf(endNode);

		if (startNodeIndex >= 0 && endNodeIndex >= 0) {

			if (startNodeIndex < endNodeIndex) {
				for (int x = startNodeIndex + 1; x < endNodeIndex + 1; x++) {
					nodes.add(wayNodes.get(x));
				}
			}
			else {
				appendNodesBackwards(nodes, startNodeIndex, endNodeIndex);
			}
		}
		else {
			throw new AnalyzerException("Cannot find given node in current way nodes");
		}
	}

	protected void appendNodesBackwards(Collection<Node> nodes, int startNodeIndex, int endNodeIndex) {
		for (int x = startNodeIndex - 1; x >= endNodeIndex; x--) {
			nodes.add(wayNodes.get(x));
		}
	}

	@Override
	public boolean containsNodes(Node... nodes) {
		for (Node node : nodes) {
			if (!wayNodes.contains(node))
				return false;
		}
		return true;
	}

	@Override
	public boolean canConnectNodesInDirection(Node startNode, Node endNode) {
		return containsNodes(startNode, endNode);
	}
}
