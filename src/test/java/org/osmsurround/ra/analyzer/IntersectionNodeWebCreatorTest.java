package org.osmsurround.ra.analyzer;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.osmsurround.ra.HelperService;
import org.osmsurround.ra.TestBase;
import org.osmsurround.ra.TestUtils;
import org.osmsurround.ra.data.Node;
import org.osmsurround.ra.segment.ISegment;
import org.springframework.beans.factory.annotation.Autowired;

public class IntersectionNodeWebCreatorTest extends TestBase {

	@Autowired
	private HelperService helperService;

	@Test
	public void testCreateWebStarFixed() throws Exception {

		List<ISegment> segments = new ArrayList<ISegment>();
		segments.add(TestUtils.asFlexibleOrderWay(1, 2, 3, 4));
		segments.add(TestUtils.asFlexibleOrderWay(4, 5, 6, 7));
		segments.add(TestUtils.asFlexibleOrderWay(4, 8, 9, 10));

		IntersectionNodeWebCreator intersectionNodeWebCreator = new IntersectionNodeWebCreator(segments);
		IntersectionNode intersectionNode = intersectionNodeWebCreator.createWebStartingWithNode(TestUtils.getNode(4));

		assertEquals(3, countEdges(intersectionNode));
	}

	@Test
	public void testCreateWebStarFlexible() throws Exception {

		List<ISegment> segments = new ArrayList<ISegment>();
		segments.add(TestUtils.asFlexibleOrderWay(1, 2, 3));
		segments.add(TestUtils.asFlexibleOrderWay(4, 3));
		segments.add(TestUtils.asFlexibleOrderWay(5, 6, 1));

		IntersectionNodeWebCreator intersectionNodeWebCreator = new IntersectionNodeWebCreator(segments);
		IntersectionNode intersectionNode = intersectionNodeWebCreator.createWebStartingWithNode(TestUtils.getNode(3));

		assertEquals(2, countEdges(intersectionNode));

		Map<Node, IntersectionNode> nodeMap = intersectionNodeWebCreator.getNodeMap();

		assertEquals(1, countEdges(nodeMap.get(TestUtils.getNode(5))));
		assertEquals(1, countEdges(nodeMap.get(TestUtils.getNode(4))));

		assertEquals(2, intersectionNodeWebCreator.getLeaves().size());
	}

	@Test
	public void testRelation12320() throws Exception {

		Map<String, List<AggregatedSegment>> aggregatedRelation = helperService
				.loadSplittedAndAggregatedRelation(TestUtils.RELATION_12320_NECKARTAL_WEG);
		List<AggregatedSegment> list = aggregatedRelation.get("");

		//		helperService.exportSimple(HelperService.convert(list.get(0).getSegments()));

		IntersectionNodeWebCreator intersectionNodeWebCreator = new IntersectionNodeWebCreator(list.get(0)
				.getSegments());
		IntersectionNode intersectionNode = intersectionNodeWebCreator.createWeb();

		helperService.exportGpx(intersectionNode, intersectionNodeWebCreator);

		int count = countEdges(intersectionNode);
		assertEquals(2, count);

		Node node = findNodeWithId(intersectionNode, 418151004);
		assertNotNull(node);

		assertEquals(1, intersectionNodeWebCreator.getLeaves().size());
	}

	private int countEdges(IntersectionNode intersectionNode) {
		int count = 0;
		for (Iterator<Edge> it = intersectionNode.getEdgesIterator(); it.hasNext();) {
			it.next();
			count++;
		}
		return count;
	}

	private Set<IntersectionNode> visitedNodes = new HashSet<IntersectionNode>();

	private Node findNodeWithId(IntersectionNode startNode, long id) {
		visitedNodes.add(startNode);
		if (startNode.getNode().getId() == id)
			return startNode.getNode();
		else {
			for (Iterator<Edge> it = startNode.getEdgesIterator(); it.hasNext();) {
				Edge edge = it.next();
				IntersectionNode nextNode = edge.getNextNode(startNode);
				if (!visitedNodes.contains(nextNode)) {
					Node node = findNodeWithId(nextNode, id);
					if (node != null)
						return node;
				}
			}
		}
		return null;
	}
}
