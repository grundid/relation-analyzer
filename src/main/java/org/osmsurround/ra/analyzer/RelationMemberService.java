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
package org.osmsurround.ra.analyzer;

import java.util.ArrayList;
import java.util.List;

import org.osmsurround.ra.context.AnalyzerContext;
import org.osmsurround.ra.data.Member;
import org.osmsurround.ra.data.Relation;
import org.osmsurround.ra.segment.ConnectableSegment;
import org.osmsurround.ra.segment.SegmentFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RelationMemberService {

	private SegmentFactory segmentFactory;

	@Autowired
	public RelationMemberService(SegmentFactory segmentFactory) {
		this.segmentFactory = segmentFactory;
	}

	public void initSegments(AnalyzerContext analyzerContext) {

		Relation relation = analyzerContext.getRelation();
		List<ConnectableSegment> segments = createSegmentsOutOfRelationMembers(relation);
		analyzerContext.setSegments(segments);
	}

	public List<ConnectableSegment> createSegmentsOutOfRelationMembers(Relation relation) {
		List<Member> members = relation.getMembers();

		List<ConnectableSegment> segments = new ArrayList<ConnectableSegment>(members.size());
		for (Member member : members) {
			segments.add(segmentFactory.createSegment(member));
		}
		return segments;
	}
}
