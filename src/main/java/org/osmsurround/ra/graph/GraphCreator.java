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
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osmsurround.ra.data.Node;
import org.osmsurround.ra.segment.ConnectableSegment;
import org.osmsurround.ra.segment.SegmentNodes;

/**
 * <p>
 * GraphCreator creates a graph of nodes and returns the leaves of this graph. The leaves are nodes with only one edge.
 * They are basically the entry points to the graph.
 * </p>
 * 
 * <p>
 * The edges of graph contain only the nodes that are needed to connect the two nodes of the edge. This has only a
 * meaning if one deals with a roundabout. The edge will only contain the nodes between the entry and exit point of the
 * roundabout.
 * </p>
 * 
 * <p>
 * If the web is some kind of a interconnected ring, where all nodes are connected with at least two edges, the web
 * creator will return one leaf. It will be the single entry point to the ring.
 * </p>
 * 
 * <p>
 * It is for the traverser to generate a useful route through the web. Returning the leaves gives the traverser a chance
 * to traverse from A to B.
 * </p>
 * 
 * <p>
 * The analyzer uses the amount of leaves to decide if the relation is OK. For example for a route relation two leaves
 * are expected.
 * </p>
 * 
 */
public class GraphCreator {

	private Map<Node, IntersectionNode> knownNodes = new HashMap<Node, IntersectionNode>();
	private List<ConnectableSegment> segments;
	private Collection<Edge> edges = new ArrayList<Edge>();

	public GraphCreator(List<ConnectableSegment> segments) {
		this.segments = segments;
		generateGraph();
	}

	public Graph getGraph() {
		Set<IntersectionNode> leaves = new HashSet<IntersectionNode>();
		for (IntersectionNode node : knownNodes.values()) {
			if (node.isLeaf())
				leaves.add(node);
		}
		if (leaves.isEmpty() && !knownNodes.isEmpty())
			leaves.add(knownNodes.values().iterator().next());

		return new Graph(leaves, edges);
	}

	private void generateGraph() {
		for (ConnectableSegment segment : segments) {
			List<ConnectableSegment> connectingSegments = findConnectingSegments(segment);
			if (connectingSegments.isEmpty())
				handleSingleSegmentGraph(segment);
			else if (connectingSegments.size() == 1) {
				createLeafEdge(segment);
			}
			else {
				for (ConnectableSegment firstSegment : connectingSegments) {
					for (ConnectableSegment secondSegment : connectingSegments.subList(
							connectingSegments.indexOf(firstSegment) + 1, connectingSegments.size())) {
						Node commonNode1 = findCommonNode(segment, firstSegment);
						Node commondNode2 = findCommonNode(segment, secondSegment);
						if (commonNode1.equals(commondNode2)) {
							createLeafEdge(segment);
						}
						else {
							createEdge(segment, commonNode1, commondNode2);
						}
					}
				}
			}
		}
	}

	private void handleSingleSegmentGraph(ConnectableSegment segment) {
		SegmentNodes segmentNodes = segment.getSegmentNodes();
		createEdge(segment, segmentNodes.getThisNode(), segmentNodes.getOtherNode());
	}

	private void createLeafEdge(ConnectableSegment segment) {
		SegmentNodes segmentNodes = segment.getSegmentNodes();
		createEdge(segment, segmentNodes.getThisNode(), segmentNodes.getOtherNode());
	}

	private void createEdge(ConnectableSegment segment, Node firstNode, Node secondNode) {
		IntersectionNode firstIntersectionNode = createIntersectionNode(firstNode);
		IntersectionNode secondIntersectionNode = createIntersectionNode(secondNode);

		firstIntersectionNode.addEdge(secondIntersectionNode);
		secondIntersectionNode.addEdge(firstIntersectionNode);

		if (segment.canConnectNodesInDirection(firstNode, secondNode)) {
			addEdge(firstIntersectionNode, secondIntersectionNode);
		}
		if (segment.canConnectNodesInDirection(secondNode, firstNode)) {
			addEdge(secondIntersectionNode, firstIntersectionNode);
		}
	}

	private void addEdge(IntersectionNode intersectionNode1, IntersectionNode intersectionNode2) {
		Edge edge = new Edge(intersectionNode1, intersectionNode2);
		edges.add(edge);
	}

	private Node findCommonNode(ConnectableSegment segment, ConnectableSegment segmentToConntent) {
		return segment.getCommonNode(segmentToConntent);
	}

	private List<ConnectableSegment> findConnectingSegments(ConnectableSegment segmentToConnect) {
		List<ConnectableSegment> result = new ArrayList<ConnectableSegment>();
		for (ConnectableSegment segment : segments) {
			if (segment != segmentToConnect && segment.canConnect(segmentToConnect))
				result.add(segment);
		}
		return result;
	}

	private IntersectionNode createIntersectionNode(Node node) {
		IntersectionNode intersectionNode = knownNodes.get(node);
		if (intersectionNode == null) {
			intersectionNode = new IntersectionNode(node);
			knownNodes.put(node, intersectionNode);
		}
		return intersectionNode;
	}

}
