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
package org.osmsurround.ra.traverse;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.osmsurround.ra.data.Node;
import org.osmsurround.ra.dijkstra.Dijkstra;
import org.osmsurround.ra.dijkstra.Vertex;
import org.osmsurround.ra.graph.Graph;
import org.osmsurround.ra.graph.IntersectionNode;
import org.osmsurround.ra.segment.ConnectableSegment;
import org.springframework.stereotype.Service;

@Service
public class TraverseService {

	public List<Node> traverse(IntersectionNode startNode, IntersectionNode endNode) {
		SingleRouteTraverser traverser = new SingleRouteTraverser(startNode, endNode);
		return traverser.getPath();
	}

	public List<Node> traverse(Graph graph, IntersectionNode startNode, IntersectionNode endNode) {

		Dijkstra dijkstraAlgorithm = new Dijkstra(graph.getEdges());
		dijkstraAlgorithm.execute(startNode);
		List<Vertex> path = dijkstraAlgorithm.getPath(endNode);

		List<Node> result = new ArrayList<Node>();
		for (Vertex vertex : path)
			result.add(vertex.getNode());

		return result;
	}

	public List<Node> fillInNodes(List<Node> path, Collection<ConnectableSegment> segments) {

		if (path.isEmpty())
			return path;

		List<ConnectableSegment> modifyableSegments = new ArrayList<ConnectableSegment>(segments);
		List<Node> result = new ArrayList<Node>();

		Node startNode = path.get(0);
		result.add(startNode);
		for (int x = 1; x < path.size(); x++) {
			Node currentNode = path.get(x);
			fillInNodesBetweenNodes(modifyableSegments, result, startNode, currentNode);
			startNode = currentNode;
		}

		return result;

	}

	public static void fillInNodesBetweenNodes(List<ConnectableSegment> modifyableSegments, List<Node> resultNodes,
			Node startNode, Node currentNode) {
		for (Iterator<ConnectableSegment> it = modifyableSegments.iterator(); it.hasNext();) {
			ConnectableSegment connectableSegment = it.next();

			if (connectableSegment.containsNodes(startNode, currentNode)) {
				int prevSize = resultNodes.size();
				connectableSegment.appendNodesBetween(resultNodes, startNode, currentNode);
				// If the segment list is only 2 elements we must make sure 
				// that we not reuse the same segment again
				// Scenario: A => B => A
				if (modifyableSegments.size() <= 2) {
					it.remove();
				}
				if (prevSize < resultNodes.size())
					break;
			}
		}
	}
}
