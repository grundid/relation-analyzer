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

	private List<IntersectionNode> addEdgesToNewNodes(List<IntersectionNode> oldIntersectionNodes) {

		List<IntersectionNode> newIntersectionNodes = new ArrayList<IntersectionNode>();

		for (IntersectionNode intersectionNode : oldIntersectionNodes) {
			ConnectableNode connectableNode = new ConnectableNode(intersectionNode.getNode());
			for (ISegment segment : segments) {
				if (segment.getStartNodes().isConnectable(connectableNode)) {
					List<ISegment> connectionSegments = findConnectingSegments(segment.getEndNodes());

					if (connectionSegments.isEmpty()) {
						List<Node> nodesTillEnd = segment.getNodesTillEnd(connectableNode);
						addEdge(newIntersectionNodes, intersectionNode, nodesTillEnd);
					}
					else {
						for (ISegment connectedSegment : connectionSegments) {
							if (segment.getEndNodes().isConnectable(connectedSegment.getStartNodes())) {
								List<Node> nodesBetween = segment.getNodesBetween(connectableNode,
										connectedSegment.getStartNodes()); //  segment.getEndNodes() sollten eine node bei conneectedSegment.getStartNodes haben
								addEdge(newIntersectionNodes, intersectionNode, nodesBetween);
							}
						}
					}
				}
				else if (segment.getEndNodes().isConnectable(connectableNode)) {

					List<ISegment> connectionSegments = findConnectingSegments(segment.getStartNodes());

					if (connectionSegments.isEmpty()) {
						List<Node> nodesTillEnd = segment.getNodesTillEnd(connectableNode);
						addEdge(newIntersectionNodes, intersectionNode, nodesTillEnd);
					}
					else {
						for (ISegment connectedSegment : connectionSegments) {
							List<Node> nodesBetween = segment.getNodesBetween(connectedSegment.getStartNodes(),
									connectableNode);
							addEdge(newIntersectionNodes, intersectionNode, nodesBetween);
						}
					}
				}
			}
		}
		return newIntersectionNodes;
	}

	private void addEdge(List<IntersectionNode> newIntersectionNodes, IntersectionNode intersectionNode,
			List<Node> nodesBetween) {
		Node lastNode = nodesBetween.get(nodesBetween.size() - 1);
		IntersectionNode endNode = null;
		if (nodeMap.containsKey(lastNode)) {
			endNode = nodeMap.get(lastNode);
		}
		else {
			endNode = createIntersectionNode(lastNode);
			newIntersectionNodes.add(endNode);
		}
		intersectionNode.addEdge(nodesBetween, endNode);
	}

	private List<ISegment> findConnectingSegments(ConnectableNode endNode) {
		List<ISegment> result = new ArrayList<ISegment>();
		for (ISegment segment : segments) {
			if (segment.getStartNodes().isConnectable(endNode))
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

}
