package org.osmsurround.ra.analyzer;

import java.util.Collection;

import org.osmsurround.ra.data.Way;

public class FixedRoundaboutWay extends FixedOrderWay {

	public FixedRoundaboutWay(Way way, boolean reverse) {
		super(way, reverse);
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
		return asCollection(new ConnectableNode(way.getNodes()));
	}
}
