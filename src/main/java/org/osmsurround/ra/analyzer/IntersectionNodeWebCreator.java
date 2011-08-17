package org.osmsurround.ra.analyzer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
 * creator will return two leaves which are the same node. They will be the entry point to the ring.
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

						leaves.add(newLeafNode);
					}
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

				List<ISegment> connectionSegments = findConnectingSegmentsButNotMe(
						segment.getOppositeNode(nodeToConnect), nodeToConnect);

				if (connectionSegments.isEmpty()) {
					List<Node> nodesTillEnd = segment.getNodesTillEnd(nodeToConnect);
					edges.add(nodesTillEnd);
				}
				else {
					for (ISegment connectedSegment : connectionSegments) {
						List<Node> nodesBetween = segment.getNodesBetween(nodeToConnect,
								connectedSegment.getStartNodes());
						edges.add(nodesBetween);
					}
				}

				it.remove();
			}
		}

		return edges;
	}

	private List<ISegment> findConnectingSegmentsButNotMe(ConnectableNode endNode, ConnectableNode nodeToIgnore) {
		List<ISegment> result = new ArrayList<ISegment>();
		for (ISegment segment : segments) {
			if (segment.canConnectForwardOnly(endNode, nodeToIgnore))
				result.add(segment);
		}
		return result;
	}

	private void initFirstLeaves() {
		ISegment segmentToRemove = null;

		for (ISegment segment : segments) {
			ConnectableNode startNodes = segment.getStartNodes();
			if (startNodes.size() == 2) {
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
