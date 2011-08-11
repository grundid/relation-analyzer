package org.osmsurround.ra.analyzer;

import java.util.Collection;

import org.osmsurround.ra.data.Way;

public class FlexibleRoundaboutWay extends FlexibleOrderWay {

	public FlexibleRoundaboutWay(Way way) {
		super(way);
	}

	@Override
	protected Collection<ConnectableNode> getStartAndEndNodes() {
		return asCollection(new ConnectableNode(way.getNodes()));
	}
}
