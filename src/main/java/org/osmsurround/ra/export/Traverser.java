package org.osmsurround.ra.export;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.osmsurround.ra.analyzer.Edge;
import org.osmsurround.ra.analyzer.IntersectionNode;
import org.osmsurround.ra.data.Node;

public class Traverser {

	private Map<IntersectionNode, Iterator<Edge>> visitedNodes = new HashMap<IntersectionNode, Iterator<Edge>>();
	private List<Node> nodes = new ArrayList<Node>();
	private final int allNodes;

	public Traverser(IntersectionNode startNode, int allNodes) {
		this.allNodes = allNodes;
		traverseNodes(startNode);
	}

	public List<Node> getNodes() {
		return nodes;
	}

	private void traverseNodes(IntersectionNode startNode) {
		if (visitedNodes.size() < allNodes) {

			Iterator<Edge> edgeIterator = null;

			if (visitedNodes.containsKey(startNode)) {
				edgeIterator = visitedNodes.get(startNode);
			}
			else {
				edgeIterator = startNode.getEdgesIterator();
				visitedNodes.put(startNode, edgeIterator);
			}

			for (; edgeIterator.hasNext();) {
				Edge edge = edgeIterator.next();
				nodes.addAll(edge.getNodes());
				IntersectionNode nextNode = edge.getNextNode(startNode);
				if (!nextNode.getNode().equals(startNode.getNode()))
					traverseNodes(nextNode);
			}
		}
	}
}
