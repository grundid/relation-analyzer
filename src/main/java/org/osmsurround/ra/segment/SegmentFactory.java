/**
 * This file is part of Relation Analyzer for OSM.
 * Copyright (c) 2001 by Adrian Stabiszewski, as@grundid.de
 *
 * Relation Analyzer is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Relation Analyzer is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with Relation Analyzer.  If not, see <http://www.gnu.org/licenses/>.
 */
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

		if ("forward".equals(memberRole))
			return createFixedSegment(way, false);
		else if ("backward".equals(memberRole))
			return createFixedSegment(way, true);
		else
			return createFlexibleSegment(way);
	}

	private ConnectableSegment createFlexibleSegment(Way way) {
		return new FlexibleWay(way.getNodes());
	}

	private ConnectableSegment createFixedSegment(Way way, boolean reverse) {
		if (reverse) {
			List<Node> nodes = new ArrayList<Node>(way.getNodes());
			Collections.reverse(nodes);
			return new FixedWay(nodes);
		}
		else
			return new FixedWay(way.getNodes());
	}
}
