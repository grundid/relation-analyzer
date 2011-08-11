package org.osmsurround.ra.analyzer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.osmsurround.ra.data.Node;
import org.osmsurround.ra.data.Way;

public class AggregatedSegment implements ISegment {

	private List<ISegment> segments = new ArrayList<ISegment>();

	public AggregatedSegment(ISegment segment) {
		segments.add(segment);
	}

	@Override
	public Collection<Node> getFirstNode() {
		return segments.get(0).getFirstNode();
	}

	@Override
	public Collection<Node> getLastNode() {
		return segments.get(segments.size() - 1).getLastNode();
	}

	@Override
	public void reverse() {
		for (ISegment segment : segments) {
			segment.reverse();
		}
		Collections.reverse(segments);
	}

	@Override
	public boolean isReversible() {
		for (ISegment segment : segments) {
			if (!segment.isReversible())
				return false;
		}
		return true;
	}

	@Override
	public List<ISegment> getSegments() {
		return segments;
	}

	@Override
	public List<Node> getNodesFromTo(Node firstNode, Node lastNode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Way> getWays() {
		List<Way> ways = new ArrayList<Way>();
		for (ISegment segment : segments) {
			ways.addAll(segment.getWays());
		}
		return ways;
	}

}
