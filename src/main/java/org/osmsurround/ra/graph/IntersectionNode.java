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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.osmsurround.ra.data.Node;
import org.osmsurround.ra.dijkstra.Vertex;

public class IntersectionNode implements Vertex {

	private Node node;

	private List<IntersectionNode> edges = new ArrayList<IntersectionNode>();

	public IntersectionNode(Node node) {
		this.node = node;
	}

	@Override
	public Node getNode() {
		return node;
	}

	public void addEdge(IntersectionNode endNode) {
		edges.add(endNode);
	}

	public Iterator<IntersectionNode> getEdgesIterator() {
		return edges.iterator();
	}

	public boolean isLeaf() {
		return edges.size() == 1;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((node == null) ? 0 : node.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IntersectionNode other = (IntersectionNode)obj;
		if (node == null) {
			if (other.node != null)
				return false;
		}
		else if (!node.equals(other.node))
			return false;
		return true;
	}

}
