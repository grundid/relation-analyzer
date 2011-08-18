package org.osmsurround.ra.analyzer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.osmsurround.ra.AnalyzerException;
import org.osmsurround.ra.data.Node;
import org.osmsurround.ra.segment.ISegment;

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

	private Collection<IntersectionNode> leaves = new ConcurrentLinkedQueue<IntersectionNode>();
	private Map<Node, IntersectionNode> knownNodes = new HashMap<Node, IntersectionNode>();
	private List<ISegment> segments;

	public IntersectionNodeWebCreator(List<ISegment> segments) {
		this.segments = segments;
	}

	public Collection<IntersectionNode> getLeaves() {
		return leaves;
	}

	public void createWeb() {
		for (ISegment segment : segments) {
			List<ISegment> connectingSegments = findConnectingSegments(segment);
			if (connectingSegments.isEmpty()) {
				List<Node> nodes = segment.getNodesTillEnd(segment.getStartNodes());
				createEdge(nodes);

			}
			else {
				if (connectingSegments.size() == 1) {
					Node commondNode = findCommonNode(segment, connectingSegments.iterator().next());
					List<Node> nodes = segment.getNodesTillEnd(new ConnectableNode(commondNode));
					createEdge(nodes);
				}
				else {
					for (ISegment firstSegment : connectingSegments) {
						for (ISegment secondSegment : connectingSegments.subList(
								connectingSegments.indexOf(firstSegment) + 1, connectingSegments.size())) {
							Node commondNode1 = findCommonNode(segment, firstSegment);
							Node commondNode2 = findCommonNode(segment, secondSegment);
							if (commondNode1.equals(commondNode2)) {
								List<Node> nodes = segment.getNodesTillEnd(new ConnectableNode(commondNode1));
								createEdge(nodes);
							}
							else {
								List<Node> nodes = segment.getNodesBetween(new ConnectableNode(commondNode1),
										new ConnectableNode(commondNode2));
								createEdge(nodes);
							}
						}
					}
				}
			}
		}
		for (IntersectionNode node : knownNodes.values()) {
			if (node.getEdgesAmount() == 1)
				leaves.add(node);
		}
		if (leaves.isEmpty() && !knownNodes.isEmpty())
			leaves.add(knownNodes.values().iterator().next());
	}

	private void createEdge(List<Node> nodes) {
		Node firstNode = nodes.get(0);
		Node secondNode = nodes.get(nodes.size() - 1);

		IntersectionNode intersectionNode1 = createIntersectionNode(firstNode);
		IntersectionNode intersectionNode2 = createIntersectionNode(secondNode);

		intersectionNode1.addEdge(nodes, intersectionNode2);
		intersectionNode2.addEdge(nodes, intersectionNode1);
	}

	private Node findCommonNode(ISegment segment, ISegment segmentToConntent) {
		return segment.getCommonNode(segmentToConntent.getEndpointNodes());
	}

	private List<ISegment> findConnectingSegments(ISegment segmentToConnect) {
		ConnectableNode endPoints = segmentToConnect.getEndpointNodes();
		List<ISegment> result = new ArrayList<ISegment>();
		for (ISegment segment : segments) {
			if (segment != segmentToConnect && segment.canConnect(endPoints))
				result.add(segment);
		}
		return result;
	}

	public void createWebOld() {
		initFirstLeaves();
		int lastSegmentsSize = segments.size();
		do {
			lastSegmentsSize = segments.size();
			for (Iterator<IntersectionNode> it = leaves.iterator(); it.hasNext();) {
				IntersectionNode intersectionNode = it.next();

				List<List<Node>> edgesForNode = findEdgesForNode(intersectionNode);

				if (!edgesForNode.isEmpty()) {
					it.remove(); // not a true leaf since it has more than one edge

					for (List<Node> edgeNodes : edgesForNode) {

						IntersectionNode newLeafNode = createIntersectionNode(edgeNodes.get(edgeNodes.size() - 1));
						intersectionNode.addEdge(edgeNodes, newLeafNode);
						newLeafNode.addEdge(edgeNodes, intersectionNode);

						if (!leaves.contains(newLeafNode))
							leaves.add(newLeafNode);
					}
				}
				else {
					if (intersectionNode.getEdgesAmount() > 1 && leaves.size() > 1)
						leaves.remove(intersectionNode);
				}
			}

		} while (lastSegmentsSize != segments.size());
	}

	private List<List<Node>> findEdgesForNode(IntersectionNode intersectionNode) {
		ConnectableNode nodeToConnect = new ConnectableNode(intersectionNode.getNode());

		List<List<Node>> edges = new ArrayList<List<Node>>();

		for (Iterator<ISegment> it = segments.iterator(); it.hasNext();) {
			ISegment segment = it.next();
			if (segment.canConnect(nodeToConnect)) {

				Collection<Node> connectionEndpoints = findConnectionEndpoints(segment.getOppositeNode(nodeToConnect),
						nodeToConnect);

				if (connectionEndpoints.isEmpty()) {
					List<Node> nodesTillEnd = segment.getNodesTillEnd(nodeToConnect);
					edges.add(nodesTillEnd);
				}
				else {
					for (Node endPoint : connectionEndpoints) {
						List<Node> nodesBetween = segment.getNodesBetween(nodeToConnect, new ConnectableNode(endPoint));
						edges.add(nodesBetween);
					}
				}

				it.remove();
			}
		}

		return edges;
	}

	private Collection<Node> findConnectionEndpoints(ConnectableNode endNode, ConnectableNode nodeToIgnore) {
		Set<Node> result = new HashSet<Node>();
		for (ISegment segment : segments) {
			if (segment.canConnectExcept(endNode, nodeToIgnore))
				result.add(segment.getCommonNode(endNode));
		}
		return result;
	}

	private void initFirstLeaves() {
		ISegment segmentToRemove = null;

		for (ISegment segment : segments) {
			ConnectableNode startNodes = segment.getStartNodes();
			if (startNodes.size() <= 2) {
				segmentToRemove = segment;

				Node firstNode = startNodes.getNodesIterator().next();

				ConnectableNode connectableNode = new ConnectableNode(firstNode);
				ConnectableNode oppositeNode = segment.getOppositeNode(connectableNode);
				Node secondNode = oppositeNode.getNodesIterator().next();

				List<Node> nodesBetween = segment.getNodesBetween(connectableNode, oppositeNode);

				IntersectionNode intersectionNode1 = createIntersectionNode(firstNode);
				IntersectionNode intersectionNode2 = createIntersectionNode(secondNode);

				intersectionNode1.addEdge(nodesBetween, intersectionNode2);
				intersectionNode2.addEdge(nodesBetween, intersectionNode1);

				leaves.add(intersectionNode1);
				leaves.add(intersectionNode2);
				break;
			}
		}
		if (segmentToRemove == null)
			throw new AnalyzerException("Cannot determine a start node");
		else
			segments.remove(segmentToRemove);

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
