package org.osmsurround.ra.segment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.osmsurround.ra.analyzer.RoundaboutService;
import org.osmsurround.ra.data.Member;
import org.osmsurround.ra.data.Node;
import org.osmsurround.ra.data.Way;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SegmentFactory {

	@Autowired
	private RoundaboutService roundaboutService;

	public ConnectableSegment createSegment(Member member) {
		Way way = member.getWay();

		if (roundaboutService.isRoundabout(way))
			return new RoundaboutWay(way.getNodes());

		String memberRole = member.getRole();

		if ("".equals(memberRole))
			return createFlexibleSegment(way);
		else if ("forward".equals(memberRole))
			return createFixedSegment(way, false);
		else if ("backward".equals(memberRole))
			return createFixedSegment(way, true);

		throw new RuntimeException("Unknown member role");
	}

	private ConnectableSegment createFlexibleSegment(Way way) {
		return new FlexibleWay(way.getNodes());
	}

	private ConnectableSegment createFixedSegment(Way way, boolean reverse) {
		List<Node> nodes = new ArrayList<Node>(way.getNodes());
		if (reverse) {
			Collections.reverse(nodes);
			return new FixedWay(nodes);
		}
		else
			return new FixedWay(way.getNodes());
	}
}
