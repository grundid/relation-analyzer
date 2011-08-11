package org.osmsurround.ra.analyzer;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.osmsurround.ra.data.Node;
import org.osmsurround.ra.data.Way;

public class FixedOrderWay implements ISegment {

	protected Way way;
	protected boolean reverse;

	public FixedOrderWay(Way way, boolean reverse) {
		this.way = way;
		this.reverse = reverse;
	}

	@Override
	public boolean isReversible() {
		return false;
	}

	@Override
	public void reverse() {
	}

	@Override
	public List<ISegment> getSegments() {
		return Collections.unmodifiableList(Arrays.asList((ISegment)this));
	}

	@Override
	public Collection<Node> getFirstNode() {
		if (reverse)
			return getNodeAtIndexAsSet(way.getNodes().size() - 1);
		else
			return getNodeAtIndexAsSet(0);
	}

	@Override
	public Collection<Node> getLastNode() {
		if (reverse)
			return getNodeAtIndexAsSet(0);
		else
			return getNodeAtIndexAsSet(way.getNodes().size() - 1);
	}

	private SingleNodeSet getNodeAtIndexAsSet(int index) {
		List<Node> nodes = way.getNodes();
		return new SingleNodeSet(nodes.get(index));
	}

	@Override
	public List<Node> getNodesFromTo(Node firstNode, Node lastNode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Way> getWays() {
		return Arrays.asList(way);
	}

}
