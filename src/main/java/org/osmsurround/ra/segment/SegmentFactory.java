package org.osmsurround.ra.segment;

import org.osmsurround.ra.analyzer.RoundaboutService;
import org.osmsurround.ra.data.Member;
import org.osmsurround.ra.data.Way;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SegmentFactory {

	private static boolean CREATE_FLEXIBLE_ONLY = false;
	@Autowired
	private RoundaboutService roundaboutService;

	public ISegment createSegment(Member member) {
		Way way = member.getWay();

		if (roundaboutService.isRoundabout(way))
			return new RoundaboutWay(way);

		if (CREATE_FLEXIBLE_ONLY)
			return createFlexibleSegment(way);

		String memberRole = member.getRole();

		if ("".equals(memberRole))
			return createFlexibleSegment(way);
		else if ("forward".equals(memberRole))
			return createFixedSegment(way, false);
		else if ("backward".equals(memberRole))
			return createFixedSegment(way, true);

		throw new RuntimeException("Unknown member role");
	}

	private ISegment createFlexibleSegment(Way way) {
		return new FlexibleOrderWay(way);
	}

	private ISegment createFixedSegment(Way way, boolean reverse) {
		return new FixedOrderWay(way, reverse);
	}
}
