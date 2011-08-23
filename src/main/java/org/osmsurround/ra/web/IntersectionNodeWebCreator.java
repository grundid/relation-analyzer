package org.osmsurround.ra.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osmsurround.ra.analyzer.ConnectableNode;
import org.osmsurround.ra.data.Node;
import org.osmsurround.ra.segment.ConnectableSegment;

/**
 * <p>
 * IntersectionNodeWebCreator creates a web of nodes and returns the leaves of this web. The leaves are nodes with only
 * one edge. They are basically the entry points to the web.
 * </p>
 * 
 * <p>
 * The edges of the web contain only the nodes that are needed to connect the two nodes of the edge. This has only a
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
public class IntersectionNodeWebCreator {

	private Map<Node, IntersectionNode> knownNodes = new HashMap<Node, IntersectionNode>();
	private List<ConnectableSegment> segments;

	public IntersectionNodeWebCreator(List<ConnectableSegment> segments) {
		this.segments = segments;
	}

	public IntersectionWeb createWeb() {
		for (ConnectableSegment segment : segments) {
			List<ConnectableSegment> connectingSegments = findConnectingSegments(segment);
			if (connectingSegments.isEmpty()) {
				List<Node> nodes = segment.getNodesTillEnd(segment.getStartNodes());
				createLeafEdge(nodes);

			}
			else {
				if (connectingSegments.size() == 1) {
					Node commondNode = findCommonNode(segment, connectingSegments.iterator().next());
					List<Node> nodes = segment.getNodesTillEnd(new ConnectableNode(commondNode));
					createLeafEdge(nodes);
				}
				else {
					for (ConnectableSegment firstSegment : connectingSegments) {
						for (ConnectableSegment secondSegment : connectingSegments.subList(
								connectingSegments.indexOf(firstSegment) + 1, connectingSegments.size())) {
							Node commondNode1 = findCommonNode(segment, firstSegment);
							Node commondNode2 = findCommonNode(segment, secondSegment);
							if (commondNode1.equals(commondNode2)) {
								List<Node> nodes = segment.getNodesTillEnd(new ConnectableNode(commondNode1));
								createLeafEdge(segment, nodes);
							}
							else {
								createEdge(commondNode1, commondNode2);
							}
						}
					}
				}
			}
		}

		return initIntersetionWeb();
	}

	private IntersectionWeb initIntersetionWeb() {
		Set<IntersectionNode> leaves = new HashSet<IntersectionNode>();
		for (IntersectionNode node : knownNodes.values()) {
			if (node.isLeaf())
				leaves.add(node);
		}
		if (leaves.isEmpty() && !knownNodes.isEmpty())
			leaves.add(knownNodes.values().iterator().next());

		return new IntersectionWeb(leaves);
	}

	private void createEdge(Node firstNode, Node secondNode) {
		IntersectionNode intersectionNode1 = createIntersectionNode(firstNode);
		IntersectionNode intersectionNode2 = createIntersectionNode(secondNode);

		intersectionNode1.addEdge(intersectionNode2);
		intersectionNode2.addEdge(intersectionNode1);
	}

	private Node findCommonNode(ConnectableSegment segment, ConnectableSegment segmentToConntent) {
		return segment.getCommonNode(segmentToConntent.getEndpointNodes());
	}

	private List<ConnectableSegment> findConnectingSegments(ConnectableSegment segmentToConnect) {
		ConnectableNode endPoints = segmentToConnect.getEndpointNodes();
		List<ConnectableSegment> result = new ArrayList<ConnectableSegment>();
		for (ConnectableSegment segment : segments) {
			if (segment != segmentToConnect && segment.canConnect(endPoints))
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
