package org.osmsurround.ra.segment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.osmsurround.ra.AnalyzerException;
import org.osmsurround.ra.analyzer.ConnectableNode;
import org.osmsurround.ra.data.Node;
import org.osmsurround.ra.data.Way;

public class FlexibleOrderWay extends FixedOrderWay {

	public FlexibleOrderWay(Way way) {
		super(way);
	}

	@Override
	public ConnectableNode getStartNodes() {
		return getStartAndEndNodes();
	}

	@Override
	public ConnectableNode getEndNodes() {
		return getStartAndEndNodes();
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
		throw new AnalyzerException("Cannot find nodes between");
	}

	protected ConnectableNode getStartAndEndNodes() {
		return asCollection(getNodeAtIndex(getWayNodes().size() - 1), getNodeAtIndex(0));
	}
}
