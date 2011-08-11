package org.osmsurround.ra.analyzer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osmsurround.ra.data.Member;
import org.osmsurround.ra.data.Relation;
import org.osmsurround.ra.segment.ISegment;
import org.osmsurround.ra.segment.SegmentFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

	private static final String DEFAULT = "";

	@Autowired
	private SegmentFactory segmentFactory;

	public Map<String, List<ISegment>> splitRelationByRole(Relation relation) {
		Map<String, List<ISegment>> resultMap = new HashMap<String, List<ISegment>>();

		List<Member> members = relation.getMembers();

		List<ISegment> segments = new ArrayList<ISegment>(members.size());
		for (Member member : members) {
			segments.add(segmentFactory.createSegment(member));
		}
		resultMap.put(DEFAULT, segments);

		return resultMap;
	}
}
