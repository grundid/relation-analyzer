package org.osmsurround.ra.analyzer;

import java.util.ArrayList;
import java.util.Collection;

import org.osmsurround.ra.data.Node;
import org.osmsurround.ra.data.Way;

public class FlexibleRoundaboutWay extends FlexibleOrderWay {

	public FlexibleRoundaboutWay(Way way, boolean reverse) {
		super(way, reverse);
	}

	@Override
	public Collection<Node> getFirstNode() {
		return new ArrayList<Node>(way.getNodes());
	}

	@Override
	public Collection<Node> getLastNode() {
		return new ArrayList<Node>(way.getNodes());
	}
}
