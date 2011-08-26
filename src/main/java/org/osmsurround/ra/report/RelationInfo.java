package org.osmsurround.ra.report;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import org.springframework.format.annotation.DateTimeFormat;

public class RelationInfo {

	private long relationId;
	@DateTimeFormat(style = "MM")
	private Calendar timestamp;
	private String user;

	private String name;
	private String type;

	private Collection<RelationTag> tags = new ArrayList<RelationTag>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Collection<RelationTag> getTags() {
		return tags;
	}

	public void setTags(Collection<RelationTag> tags) {
		this.tags = tags;
	}

	public void setRelationId(long relationId) {
		this.relationId = relationId;
	}

	public void setTimestamp(Calendar timestamp) {
		this.timestamp = timestamp;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public long getRelationId() {
		return relationId;
	}

	public Calendar getTimestamp() {
		return timestamp;
	}

	public String getUser() {
		return user;
	}

}
