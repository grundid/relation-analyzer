package org.osmsurround.ra.report;

import java.util.Calendar;

import org.springframework.format.annotation.DateTimeFormat;

public class RelationInfo {

	private long relationId;
	@DateTimeFormat(style = "MM")
	private Calendar timestamp;
	private String user;

	public RelationInfo(long relationId, Calendar timestamp, String user) {
		this.relationId = relationId;
		this.timestamp = timestamp;
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
