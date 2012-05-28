package org.osmsurround.ra;

import java.util.ArrayList;
import java.util.List;

import org.osmsurround.ra.segment.ConnectableSegment;

public class SegmentsBuilder {

	private List<ConnectableSegment> segments = new ArrayList<ConnectableSegment>();

	public SegmentsBuilder appendFlexible(long... nodeIds) {
		segments.add(TestUtils.asFlexibleOrderWay(nodeIds));
		return this;
	}

	public SegmentsBuilder appendFixed(long... nodeIds) {
		segments.add(TestUtils.asFixedOrderWay(nodeIds));
		return this;
	}

	public SegmentsBuilder appendRoundabout(long... nodeIds) {
		segments.add(TestUtils.asRoundaboutWay(nodeIds));
		return this;
	}

	public static SegmentsBuilder create() {
		return new SegmentsBuilder();
	}

	public List<ConnectableSegment> getSegments() {
		return segments;
	}
}
