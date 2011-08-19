package org.osmsurround.ra.analyzer;

import java.util.ArrayList;
import java.util.List;

import org.osmsurround.ra.context.AnalyzerContext;
import org.osmsurround.ra.data.Member;
import org.osmsurround.ra.data.Relation;
import org.osmsurround.ra.segment.ISegment;
import org.osmsurround.ra.segment.SegmentFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RelationMemberService {

	@Autowired
	private SegmentFactory segmentFactory;

	public void initSegments(AnalyzerContext analyzerContext) {

		Relation relation = analyzerContext.getRelation();
		List<Member> members = relation.getMembers();

		List<ISegment> segments = new ArrayList<ISegment>(members.size());
		for (Member member : members) {
			segments.add(segmentFactory.createSegment(member));
		}

		analyzerContext.setSegments(segments);
	}
}
