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
	private double length;

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

	public double getLength() {
		return length;
	}

	public void setLength(double length) {
		this.length = length;
	}

}
