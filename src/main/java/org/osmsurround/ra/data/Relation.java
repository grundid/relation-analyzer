package org.osmsurround.ra.data;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class Relation implements Serializable {

	private long relationId;
	private List<Member> members;
	private Calendar timestamp;
	private String user;
	private Map<String, String> tags;

	public Relation(long relationId, List<Member> members, Calendar timestamp, String user, Map<String, String> tags) {
		this.relationId = relationId;
		this.members = members;
		this.timestamp = timestamp;
		this.user = user;
		this.tags = tags;
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

	public Map<String, String> getTags() {
		return tags;
	}

}
