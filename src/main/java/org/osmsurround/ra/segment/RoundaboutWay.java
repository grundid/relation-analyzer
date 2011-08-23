package org.osmsurround.ra.segment;

import java.util.Collection;
import java.util.List;

import org.osmsurround.ra.data.Node;

public class RoundaboutWay extends FlexibleWay {

	public RoundaboutWay(List<Node> nodes) {
		super(nodes);
	}

	@Override
	protected void appendNodesBackwards(Collection<Node> nodes, int startNodeIndex, int endNodeIndex) {
		for (int x = startNodeIndex + 1; x < wayNodes.size(); x++) {
			nodes.add(wayNodes.get(x));
		}
		for (int x = 1; x < endNodeIndex + 1; x++) {
			nodes.add(wayNodes.get(x));
		}
	}
}
