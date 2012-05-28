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
package org.osmsurround.ra.graph;

import java.util.Collection;
import java.util.Set;

import org.osmsurround.ra.dijkstra.Edge;

public class Graph {

	private Set<IntersectionNode> leaves;
	private Collection<Edge> edges;
	private double length;

	@SuppressWarnings("unchecked")
	public Graph(Set<IntersectionNode> leaves, Collection<? extends Edge> edges, double length) {
		this.leaves = leaves;
		this.length = length;
		this.edges = (Collection<Edge>)edges;
	}

	public Set<IntersectionNode> getLeaves() {
		return leaves;
	}

	public Collection<Edge> getEdges() {
		return edges;
	}

	public double getLength() {
		return length;
	}
}
