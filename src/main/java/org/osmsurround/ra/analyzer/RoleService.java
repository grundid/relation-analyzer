package org.osmsurround.ra.analyzer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osmsurround.ra.data.Member;
import org.osmsurround.ra.data.Relation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

	@Autowired
	private SegmentFactory segmentFactory;

	public Map<String, List<ISegment>> splitRelationByRole(Relation relation) {
		Map<String, List<ISegment>> resultMap = new HashMap<String, List<ISegment>>();
		Set<String> relationRoles = collectRelationRoles(relation.getMembers());
		if (!relationRoles.isEmpty()) {
			if (hasSingleEmptyRole(relationRoles)) {
				putMembersWithRolename(relation.getMembers(), resultMap, "");
			}
			else {
				for (String role : relationRoles) {
					if (!role.isEmpty()) {
						putMembersWithRolename(relation.getMembers(), resultMap, role);
					}
				}
			}
		}

		return resultMap;
	}

	private void putMembersWithRolename(Collection<Member> members, Map<String, List<ISegment>> resultMap,
			String roleName) {
		List<ISegment> segments = new ArrayList<ISegment>(members.size());
		for (Member member : members) {
			if (roleName.equals(member.getRole()) || member.getRole().isEmpty()) {
				segments.add(segmentFactory.createSegment(member));
			}
		}
		resultMap.put(roleName, segments);
	}

	private boolean hasSingleEmptyRole(Set<String> relationRoles) {
		return relationRoles.size() == 1 && relationRoles.iterator().next().equals("");
	}

	private Set<String> collectRelationRoles(Iterable<Member> members) {
		Set<String> roles = new HashSet<String>();
		for (Member member : members) {
			roles.add(member.getRole());
		}
		return roles;
	}
}
