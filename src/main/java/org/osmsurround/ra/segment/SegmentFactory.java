package org.osmsurround.ra.segment;

import org.osmsurround.ra.data.Member;
import org.osmsurround.ra.data.Way;
import org.springframework.stereotype.Component;

@Component
public class SegmentFactory {

	public ConnectableSegment createSegment(Member member) {
		Way way = member.getWay();
		return new FlexibleWay(way);
	}
}
