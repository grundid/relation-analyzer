package org.osmsurround.ra.segment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.osmsurround.ra.AnalyzerException;
import org.osmsurround.ra.analyzer.ConnectableNode;
import org.osmsurround.ra.data.Node;
import org.osmsurround.ra.data.Way;

public class FixedOrderWay implements ISegment {

	private Way way;
	protected boolean reverse = false;

	public FixedOrderWay(Way way) {
		this.way = way;
	}

	public FixedOrderWay(Way way, boolean reverse) {
		this.way = way;
		this.reverse = reverse;
	}

	@Override
	public ConnectableNode getStartNodes() {
		if (reverse)
			return asCollection(getSingleEndNode());
		else
			return asCollection(getSingleStartNode());
	}

	@Override
	public ConnectableNode getEndNodes() {
		if (reverse)
			return asCollection(getSingleStartNode());
		else
			return asCollection(getSingleEndNode());
	}

	protected Node getSingleStartNode() {
		return getNodeAtIndex(0);
	}

	protected Node getSingleEndNode() {
		return getNodeAtIndex(way.getNodes().size() - 1);
	}

	@Override
	public List<Node> getNodesBetween(ConnectableNode startNode, ConnectableNode endNode) {
		List<Node> result = new ArrayList<Node>(getWayNodes());
		if (startNode.contains(getSingleStartNode()) && endNode.contains(getSingleEndNode())) {
			return result;
		}
		if (startNode.contains(getSingleEndNode()) && endNode.contains(getSingleStartNode())) {
			Collections.reverse(result);
			return result;
		}
		throw new AnalyzerException("Cannot find nodes between in way " + way.getId());
	}

	protected ConnectableNode asCollection(Node... nodes) {
		return new ConnectableNode(nodes);
	}

	protected Node getNodeAtIndex(int index) {
		List<Node> nodes = way.getNodes();
		return nodes.get(index);
	}

	protected List<Node> getWayNodes() {
		return way.getNodes();
	}

	@Override
	public List<Node> getNodesTillEnd(ConnectableNode startNode) {
		if (startNode.isConnectable(getStartNodes()))
			return getNodesBetween(startNode, getEndNodes());
		else
			return getNodesBetween(startNode, getStartNodes());
	}

	@Override
	public boolean canConnect(ConnectableNode node) {
		return node.contains(getSingleStartNode()) || node.contains(getSingleEndNode());
	}

	@Override
	public ConnectableNode getEndpointNodes() {
		return new ConnectableNode(getSingleStartNode(), getSingleEndNode());
	}

	@Override
	public Node getCommonNode(ConnectableNode connectableNode) {
		if (connectableNode.contains(getSingleStartNode()))
			return getSingleStartNode();
		if (connectableNode.contains(getSingleEndNode()))
			return getSingleEndNode();
		throw new AnalyzerException("No common nodes");
	}
}
