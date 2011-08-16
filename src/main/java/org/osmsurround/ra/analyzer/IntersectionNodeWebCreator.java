package org.osmsurround.ra.analyzer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osmsurround.ra.AnalyzerException;
import org.osmsurround.ra.data.Node;
import org.osmsurround.ra.segment.ISegment;

public class IntersectionNodeWebCreator {

	private Map<Node, IntersectionNode> nodeMap = new HashMap<Node, IntersectionNode>();
	private List<ISegment> segments;
	private List<IntersectionNode> leaves = new ArrayList<IntersectionNode>();

	public IntersectionNodeWebCreator(List<ISegment> segments) {
		this.segments = segments;
	}

	public IntersectionNode createWeb() {

		Node node = determineFirstIntersectionNode(segments);
		return createWebStartingWithNode(node);
	}

	public IntersectionNode createWebStartingWithNode(Node node) {
		IntersectionNode firstIntersectionNode = createIntersectionNode(node);

		List<IntersectionNode> newIntersectionNodes = new ArrayList<IntersectionNode>();
		newIntersectionNodes.add(firstIntersectionNode);

		do {
			newIntersectionNodes = addEdgesToNewNodes(newIntersectionNodes);
		} while (!newIntersectionNodes.isEmpty());

		return firstIntersectionNode;
	}

	private IntersectionNode createIntersectionNode(Node node) {
		IntersectionNode intersectionNode = new IntersectionNode(node);
		nodeMap.put(node, intersectionNode);
		return intersectionNode;
	}

	private List<IntersectionNode> addEdgesToNewNodes(List<IntersectionNode> intersectionNodesToConnect) {

		List<IntersectionNode> newIntersectionNodes = new ArrayList<IntersectionNode>();

		for (IntersectionNode intersectionNode : intersectionNodesToConnect) {
			ConnectableNode nodeToConnect = new ConnectableNode(intersectionNode.getNode());

			for (ISegment segment : segments) {
				if (segment.getStartNodes().isConnectable(nodeToConnect)) {
					List<ISegment> connectionSegments = findConnectingSegmentsButNotMe(
							segment.getOppositeNode(nodeToConnect), nodeToConnect);

					if (connectionSegments.isEmpty()) {
						addLeaf(newIntersectionNodes, intersectionNode, nodeToConnect, segment);
					}
					else {
						for (ISegment connectedSegment : connectionSegments) {
							List<Node> nodesBetween = segment.getNodesBetween(nodeToConnect,
									connectedSegment.getStartNodes());
							addEdge(newIntersectionNodes, intersectionNode, nodesBetween);
						}
					}
				}
				else if (segment.getEndNodes().isConnectable(nodeToConnect)) {

					List<ISegment> connectionSegments = findConnectingSegmentsButNotMe(
							segment.getOppositeNode(nodeToConnect), nodeToConnect);

					if (connectionSegments.isEmpty()) {
						addLeaf(newIntersectionNodes, intersectionNode, nodeToConnect, segment);
					}
					else {
						for (ISegment connectedSegment : connectionSegments) {
							List<Node> nodesBetween = segment.getNodesBetween(connectedSegment.getStartNodes(),
									nodeToConnect);
							addEdge(newIntersectionNodes, intersectionNode, nodesBetween);
						}
					}
				}
			}
		}
		return newIntersectionNodes;
	}

	private void addLeaf(List<IntersectionNode> newIntersectionNodes, IntersectionNode intersectionNode,
			ConnectableNode nodeToConnect, ISegment segment) {
		List<Node> nodesTillEnd = segment.getNodesTillEnd(nodeToConnect);
		addEdge(newIntersectionNodes, intersectionNode, nodesTillEnd);
	}

	private void addEdge(List<IntersectionNode> newIntersectionNodes, IntersectionNode intersectionNode,
			List<Node> nodesBetween) {
		Node lastNode = nodesBetween.get(nodesBetween.size() - 1);
		IntersectionNode endNode = null;
		if (nodeMap.containsKey(lastNode)) {
			endNode = nodeMap.get(lastNode);
			leaves.remove(endNode);
		}
		else {
			endNode = createIntersectionNode(lastNode);
			newIntersectionNodes.add(endNode);
			leaves.add(endNode);
		}
		intersectionNode.addEdge(nodesBetween, endNode);
	}

	private List<ISegment> findConnectingSegmentsButNotMe(ConnectableNode endNode, ConnectableNode nodeToIgnore) {
		List<ISegment> result = new ArrayList<ISegment>();
		for (ISegment segment : segments) {

			if (segment.getStartNodes().isConnectable(endNode) && !segment.getEndNodes().isConnectable(nodeToIgnore))
				result.add(segment);
		}

		return result;
	}

	// finde einen echten Kreuzungsknoten
	private Node determineFirstIntersectionNode(List<ISegment> segments) {
		for (ISegment segment : segments) {
			ConnectableNode startNodes = segment.getStartNodes();
			if (startNodes.size() <= 2)
				return startNodes.getNodesIterator().next(); // TODO getOriginalStartNode
		}
		throw new AnalyzerException("Cannot determine a start node");
	}

	public Map<Node, IntersectionNode> getNodeMap() {
		return nodeMap;
	}

	public List<IntersectionNode> getLeaves() {
		return leaves;
	}

	public int getNodesAmount() {
		return nodeMap.size();
	}
}
