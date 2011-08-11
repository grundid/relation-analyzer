package org.osmsurround.ra.analyzer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A connectable segment contains only segments that can be connected.
 * 
 */
public class ConnectableSegment implements ISegment {

	private List<ISegment> segments = new ArrayList<ISegment>();

	public ConnectableSegment(ISegment segment) {
		segments.add(segment);
	}

	@Override
	public Collection<ConnectableNode> getStartNodes() {
		Collection<ConnectableNode> nodes = new ArrayList<ConnectableNode>();
		for (ISegment segment : segments)
			nodes.addAll(segment.getStartNodes());
		return nodes;
	}

	@Override
	public Collection<ConnectableNode> getEndNodes() {
		Collection<ConnectableNode> nodes = new ArrayList<ConnectableNode>();
		for (ISegment segment : segments)
			nodes.addAll(segment.getEndNodes());
		return nodes;
	}

	public void addSegment(ISegment segment) {
		segments.add(segment);
	}

	public void addAllSegments(Collection<ISegment> segments) {
		this.segments.addAll(segments);
	}

	public List<ISegment> getSegments() {
		return segments;
	}
}
