package org.osmsurround.ra.segment;

import java.util.Collection;
import java.util.List;

import org.osmsurround.ra.AnalyzerException;
import org.osmsurround.ra.data.Node;

public class FixedWay extends FlexibleWay {

	public FixedWay(List<Node> nodes) {
		super(nodes);
	}

	@Override
	protected void appendNodesBackwards(Collection<Node> nodes, int startNodeIndex, int endNodeIndex) {
		throw new AnalyzerException("Cannot append nodes in the given order");
	}

	@Override
	public boolean canConnectNodesInDirection(Node startNode, Node endNode) {
		return wayNodes.indexOf(startNode) > wayNodes.indexOf(endNode);
	}
}
