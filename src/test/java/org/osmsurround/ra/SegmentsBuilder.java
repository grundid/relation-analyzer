package org.osmsurround.ra;

import java.util.ArrayList;
import java.util.List;

import org.osmsurround.ra.segment.ISegment;

public class SegmentsBuilder {

	private List<ISegment> segments = new ArrayList<ISegment>();

	public SegmentsBuilder appendFlexible(long... nodeIds) {
		segments.add(TestUtils.asFlexibleOrderWay(nodeIds));
		return this;
	}

	public SegmentsBuilder appendFixed(long... nodeIds) {
		segments.add(TestUtils.asFixedOrderWay(nodeIds));
		return this;
	}

	public static SegmentsBuilder create() {
		return new SegmentsBuilder();
	}

	public List<ISegment> getSegments() {
		return segments;
	}
}
