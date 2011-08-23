package org.osmsurround.ra.export;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.osmsurround.ra.AnalyzerException;
import org.osmsurround.ra.data.Node;
import org.osmsurround.ra.graph.IntersectionNode;

public class SingleRouteTraverser {

	private List<Node> path = new ArrayList<Node>();
	private Collection<IntersectionNode> visitedNodes = new HashSet<IntersectionNode>();
	private IntersectionNode endNode;

	public SingleRouteTraverser(IntersectionNode startNode, IntersectionNode endNode) {
		this.endNode = endNode;
		traverseNodes(startNode);
	}

	public List<Node> getPath() {
		return path;
	}

	private void traverseNodes(IntersectionNode startNode) {
		path.add(startNode.getNode());
		while (!startNode.equals(endNode)) {
			visitedNodes.add(startNode);

			Iterator<IntersectionNode> edgeIterator = startNode.getEdgesIterator();

			IntersectionNode nextNode = edgeIterator.next();

			while (visitedNodes.contains(nextNode)) {
				if (edgeIterator.hasNext()) {
					nextNode = edgeIterator.next();
				}
				else
					throw new AnalyzerException("No way to go. All nodes visited before.");
			}

			path.add(nextNode.getNode());
			startNode = nextNode;
		}
	}
}
