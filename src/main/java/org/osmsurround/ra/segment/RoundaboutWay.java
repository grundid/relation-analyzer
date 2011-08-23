package org.osmsurround.ra.segment;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.osmsurround.ra.AnalyzerException;
import org.osmsurround.ra.analyzer.ConnectableNode;
import org.osmsurround.ra.data.Node;
import org.osmsurround.ra.data.Way;

public class RoundaboutWay extends FlexibleOrderWay {

	public RoundaboutWay(Way way) {
		super(way);
	}

	@Override
	protected ConnectableNode getStartAndEndNodes() {
		return new ConnectableNode(getWayNodes());
	}

	@Override
	public List<Node> getNodesBetween(ConnectableNode startNode, ConnectableNode endNode) {

		List<Node> nodes = getWayNodes();

		int startNodeIndex = findNodeIndex(startNode, nodes);
		int endNodeIndex = findNodeIndex(endNode, nodes);

		if (startNodeIndex < endNodeIndex)
			return nodes.subList(startNodeIndex, endNodeIndex + 1);
		else {
			List<Node> result = new ArrayList<Node>();
			result.addAll(nodes.subList(startNodeIndex, nodes.size()));
			result.addAll(nodes.subList(0, endNodeIndex + 1));
			return result;
		}
	}

	private static int findNodeIndex(ConnectableNode connectableNode, List<Node> nodes) {
		for (int x = 0; x < nodes.size(); x++) {
			if (connectableNode.contains(nodes.get(x))) {
				return x;
			}
		}
		throw new AnalyzerException("Cannot find given node in way");
	}

	@Override
	public List<Node> getNodesTillEnd(ConnectableNode startNode) {
		List<Node> nodes = getWayNodes();

		int startNodeIndex = findNodeIndex(startNode, nodes);

		List<Node> result = new ArrayList<Node>();
		result.addAll(nodes.subList(startNodeIndex, nodes.size()));
		if (startNodeIndex != 0)
			result.addAll(nodes.subList(1, startNodeIndex + 1));
		return result;
	}

	@Override
	public boolean canConnect(ConnectableNode node) {
		return getCommonNodeInternal(node) != null;
	}

	@Override
	public ConnectableNode getEndpointNodes() {
		return getStartAndEndNodes();
	}

	@Override
	public Node getCommonNode(ConnectableNode connectableNode) {
		Node commonNode = getCommonNodeInternal(connectableNode);
		if (commonNode == null)
			throw new AnalyzerException("No common nodes");

		return commonNode;
	}

	private Node getCommonNodeInternal(ConnectableNode connectableNode) {
		List<Node> nodes = getWayNodes();
		for (Iterator<Node> it = connectableNode.getNodesIterator(); it.hasNext();) {
			Node externalNode = it.next();
			if (nodes.contains(externalNode))
				return externalNode;
		}
		return null;
	}
}
