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
import java.util.List;

import org.osmsurround.ra.context.AnalyzerContext;
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

	public List<Node> traverse(AnalyzerContext analyzerContext, IntersectionNode startNode, IntersectionNode endNode) {

		Graph intersectionWeb = analyzerContext.getGraphs().get(0);
		Dijkstra dijkstraAlgorithm = new Dijkstra(intersectionWeb.getEdges());
		dijkstraAlgorithm.execute(startNode);
		List<Vertex> path = dijkstraAlgorithm.getPath(endNode);

		List<Node> result = new ArrayList<Node>();

		if (!path.isEmpty()) {
			Vertex startVertex = path.get(0);
			result.add(startVertex.getNode());
			for (int x = 1; x < path.size(); x++) {

				Vertex currentVertex = path.get(x);

				for (ConnectableSegment connectableSegment : analyzerContext.getSegments()) {
					if (connectableSegment.containsNodes(startVertex.getNode(), currentVertex.getNode())) {
						connectableSegment.appendNodesBetween(result, startVertex.getNode(), currentVertex.getNode());
						break;
					}
				}

				startVertex = currentVertex;

			}
		}
		return result;
	}
}
