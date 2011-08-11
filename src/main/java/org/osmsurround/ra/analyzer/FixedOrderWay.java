package org.osmsurround.ra.analyzer;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.osmsurround.ra.data.Node;
import org.osmsurround.ra.data.Way;

public class FixedOrderWay implements ISegment {

	protected Way way;
	protected boolean reverse = false;

	public FixedOrderWay(Way way) {
		this.way = way;
	}

	public FixedOrderWay(Way way, boolean reverse) {
		this.way = way;
		this.reverse = reverse;
	}

	@Override
	public Collection<ConnectableNode> getStartNodes() {
		if (reverse)
			return asCollection(getNodeAtIndexAsConnectableNode(way.getNodes().size() - 1));
		else
			return asCollection(getNodeAtIndexAsConnectableNode(0));
	}

	@Override
	public Collection<ConnectableNode> getEndNodes() {
		if (reverse)
			return asCollection(getNodeAtIndexAsConnectableNode(0));
		else
			return asCollection(getNodeAtIndexAsConnectableNode(way.getNodes().size() - 1));
	}

	protected Collection<ConnectableNode> asCollection(ConnectableNode... nodes) {
		return Arrays.asList(nodes);
	}

	protected ConnectableNode getNodeAtIndexAsConnectableNode(int index) {
		List<Node> nodes = way.getNodes();
		return new ConnectableNode(nodes.get(index));
	}
}
