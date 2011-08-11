package org.osmsurround.ra.data;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

public class Relation implements Serializable {

	private long relationId;
	private List<Member> members;
	private Calendar timestamp;
	private String user;

	public Relation(long relationId, List<Member> members, Calendar timestamp, String user) {
		this.relationId = relationId;
		this.members = members;
		this.timestamp = timestamp;
		this.user = user;
	}

	public long getRelationId() {
		return relationId;
	}

	public List<Member> getMembers() {
		return members;
	}

	public Calendar getTimestamp() {
		return timestamp;
	}

	public String getUser() {
		return user;
	}

}
