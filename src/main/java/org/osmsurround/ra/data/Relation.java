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
