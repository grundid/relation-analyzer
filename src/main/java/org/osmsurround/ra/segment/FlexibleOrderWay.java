package org.osmsurround.ra.segment;

import org.osmsurround.ra.analyzer.ConnectableNode;
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

	protected ConnectableNode getStartAndEndNodes() {
		return asCollection(getNodeAtIndex(getWayNodes().size() - 1), getNodeAtIndex(0));
	}
}
