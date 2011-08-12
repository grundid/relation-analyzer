package org.osmsurround.ra.analyzer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.osmsurround.ra.segment.ISegment;

/**
 * A segment that contains only segments that can be connected.
 * 
 */
public class AggregatedSegment {

	private List<ISegment> segments = new ArrayList<ISegment>();

	public AggregatedSegment(ISegment segment) {
		segments.add(segment);
	}

	public AggregatedSegment(AggregatedSegment aggregatedSegment) {
		addSegment(aggregatedSegment);
	}

	public Collection<ConnectableNode> getStartNodes() {
		Collection<ConnectableNode> nodes = new ArrayList<ConnectableNode>();
		for (ISegment segment : segments)
			nodes.add(segment.getStartNodes());
		return nodes;
	}

	public Collection<ConnectableNode> getEndNodes() {
		Collection<ConnectableNode> nodes = new ArrayList<ConnectableNode>();
		for (ISegment segment : segments)
			nodes.add(segment.getEndNodes());
		return nodes;
	}

	public void addSegment(AggregatedSegment segment) {
		addAllSegments(segment.getSegments());
	}

	public void addAllSegments(Collection<ISegment> segments) {
		this.segments.addAll(segments);
	}

	public List<ISegment> getSegments() {
		return segments;
	}
}
