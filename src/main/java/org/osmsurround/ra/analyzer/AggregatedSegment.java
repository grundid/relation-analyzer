package org.osmsurround.ra.analyzer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.osmsurround.ra.segment.ConnectableSegment;

/**
 * A segment that contains only segments that can be connected.
 * 
 */
public class AggregatedSegment {

	private List<ConnectableSegment> segments = new ArrayList<ConnectableSegment>();

	public AggregatedSegment(ConnectableSegment segment) {
		segments.add(segment);
	}

	public AggregatedSegment(AggregatedSegment aggregatedSegment) {
		addSegment(aggregatedSegment);
	}

	public Collection<ConnectableNode> getStartNodes() {
		Collection<ConnectableNode> nodes = new ArrayList<ConnectableNode>();
		for (ConnectableSegment segment : segments)
			nodes.add(segment.getStartNodes());
		return nodes;
	}

	public Collection<ConnectableNode> getEndNodes() {
		Collection<ConnectableNode> nodes = new ArrayList<ConnectableNode>();
		for (ConnectableSegment segment : segments)
			nodes.add(segment.getEndNodes());
		return nodes;
	}

	public void addSegment(AggregatedSegment segment) {
		addAllSegments(segment.getSegments());
	}

	public void addAllSegments(Collection<ConnectableSegment> segments) {
		this.segments.addAll(segments);
	}

	public List<ConnectableSegment> getSegments() {
		return segments;
	}
}
