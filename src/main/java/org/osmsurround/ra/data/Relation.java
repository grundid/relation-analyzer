package org.osmsurround.ra.data;

import java.io.Serializable;
import java.util.List;

public class Relation implements Serializable {

	private long relationId;
	private List<Member> members;

	public Relation(long relationId, List<Member> members) {
		this.relationId = relationId;
		this.members = members;
	}

	public long getRelationId() {
		return relationId;
	}

	public List<Member> getMembers() {
		return members;
	}
}
