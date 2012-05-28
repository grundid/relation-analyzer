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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Way implements Serializable {

	private long id;
	private List<Node> nodes;
	private Map<String, String> tags = new HashMap<String, String>();

	public Way(long id, List<Node> nodes) {
		this.id = id;
		this.nodes = nodes;
	}

	public Way(long id, int nodeCapacity) {
		this.id = id;
		nodes = new ArrayList<Node>(nodeCapacity);
	}

	public Way(long id) {
		this.id = id;
		nodes = new ArrayList<Node>();
	}

	public void addNode(Node node) {
		nodes.add(node);
	}

	public void setTag(String key, String value) {
		tags.put(key, value);
	}

	public long getId() {
		return id;
	}

	public List<Node> getNodes() {
		return nodes;
	}

	public Map<String, String> getTags() {
		return tags;
	}
}
