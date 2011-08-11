package org.osmsurround.ra.segment;

import java.util.Collection;

import org.osmsurround.ra.analyzer.ConnectableNode;
import org.osmsurround.ra.data.Way;

public class FlexibleOrderWay extends FixedOrderWay {

	public FlexibleOrderWay(Way way) {
		super(way);
	}

	@Override
	public Collection<ConnectableNode> getStartNodes() {
		return getStartAndEndNodes();
	}

	@Override
	public Collection<ConnectableNode> getEndNodes() {
		return getStartAndEndNodes();
	}

	protected Collection<ConnectableNode> getStartAndEndNodes() {
		return asCollection(getNodeAtIndexAsConnectableNode(way.getNodes().size() - 1),
				getNodeAtIndexAsConnectableNode(0));
	}
}
