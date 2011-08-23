package org.osmsurround.ra.segment;

import java.util.Iterator;
import java.util.List;

import org.osmsurround.ra.AnalyzerException;
import org.osmsurround.ra.analyzer.ConnectableNode;
import org.osmsurround.ra.data.Node;
import org.osmsurround.ra.data.Way;

public class FlexibleWay implements ConnectableSegment {

	private Way way;

	public FlexibleWay(Way way) {
		this.way = way;
	}

	@Override
	public SegmentNodes getSegmentNodes() {
		List<Node> nodes = getWayNodes();
		return new SegmentNodes(nodes.get(0), nodes.get(nodes.size() - 1));
	}

	@Override
	public ConnectableNode getEndpointNodes() {
		return new ConnectableNode(way.getNodes());
	}

	@Override
	public Node getCommonNode(ConnectableSegment otherSegment) {
		Node commonNode = getCommonNodeInternal(otherSegment);
		if (commonNode == null)
			throw new AnalyzerException("No common nodes");

		return commonNode;
	}

	@Override
	public boolean canConnect(ConnectableSegment otherSegment) {
		Node commonNode = getCommonNodeInternal(otherSegment);
		return commonNode != null;
	}

	private Node getCommonNodeInternal(ConnectableSegment otherSegment) {
		List<Node> nodes = getWayNodes();
		for (Iterator<Node> it = otherSegment.getEndpointNodes().getNodesIterator(); it.hasNext();) {
			Node externalNode = it.next();
			if (nodes.contains(externalNode))
				return externalNode;
		}
		return null;
	}

	private List<Node> getWayNodes() {
		return way.getNodes();
	}
}
