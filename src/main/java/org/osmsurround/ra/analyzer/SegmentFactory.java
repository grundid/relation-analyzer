package org.osmsurround.ra.analyzer;

import org.osmsurround.ra.data.Member;
import org.osmsurround.ra.data.Way;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SegmentFactory {

	private static boolean CREATE_FLEXIBLE_ONLY = true;
	@Autowired
	private RoundaboutService roundaboutService;

	public ISegment createSegment(Member member) {
		Way way = member.getWay();

		boolean isRoundabout = roundaboutService.isRoundabout(way);

		if (CREATE_FLEXIBLE_ONLY)
			return createFlexibleSegment(way, isRoundabout);

		if ("".equals(member.getRole()))
			return createFlexibleSegment(way, isRoundabout);
		else if ("forward".equals(member.getRole()))
			return createFixedSegment(way, isRoundabout, false);
		else if ("backward".equals(member.getRole()))
			return createFixedSegment(way, isRoundabout, true);

		throw new RuntimeException("Unknown member role");
	}

	private ISegment createFlexibleSegment(Way way, boolean isRoundabout) {
		if (isRoundabout)
			return new FlexibleRoundaboutWay(way, false);
		else
			return new FlexibleOrderWay(way, false);
	}

	private ISegment createFixedSegment(Way way, boolean isRoundabout, boolean reverse) {
		if (isRoundabout)
			return new FixedRoundaboutWay(way, reverse);
		else
			return new FixedOrderWay(way, reverse);
	}

}
