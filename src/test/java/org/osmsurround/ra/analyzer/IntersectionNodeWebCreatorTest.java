package org.osmsurround.ra.analyzer;

import static org.junit.Assert.*;
import static org.osmsurround.ra.TestUtils.*;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.osmsurround.ra.HelperService;
import org.osmsurround.ra.SegmentsBuilder;
import org.osmsurround.ra.TestBase;
import org.springframework.beans.factory.annotation.Autowired;

public class IntersectionNodeWebCreatorTest extends TestBase {

	@Autowired
	private HelperService helperService;

	@Test
	public void testCreateWebSimple() throws Exception {
		Collection<IntersectionNode> leaves = executeAndGetLeaves(SegmentsBuilder.create().appendFlexible(1, 2, 3, 4));

		assertEquals(2, leaves.size());

		assertTrue(leaves.contains(new IntersectionNode(getNode(4))));
		assertTrue(leaves.contains(new IntersectionNode(getNode(1))));
	}

	@Test
	public void testCreateWebSimpleFixed() throws Exception {
		Collection<IntersectionNode> leaves = executeAndGetLeaves(SegmentsBuilder.create().appendFixed(1, 2, 3, 4));

		assertEquals(2, leaves.size());

		assertTrue(leaves.contains(new IntersectionNode(getNode(4))));
		assertTrue(leaves.contains(new IntersectionNode(getNode(1))));
	}

	@Test
	public void testCreateWebTwoWay() throws Exception {

		Collection<IntersectionNode> leaves = executeAndGetLeaves(SegmentsBuilder.create().appendFlexible(1, 2)
				.appendFlexible(2, 3));

		assertEquals(2, leaves.size());

		assertTrue(leaves.contains(new IntersectionNode(getNode(1))));
		assertTrue(leaves.contains(new IntersectionNode(getNode(3))));
	}

	@Test
	public void testCreateWebTwoWayFixed() throws Exception {

		Collection<IntersectionNode> leaves = executeAndGetLeaves(SegmentsBuilder.create().appendFixed(1, 2)
				.appendFixed(2, 3));

		assertEquals(2, leaves.size());

		assertTrue(leaves.contains(new IntersectionNode(getNode(1))));
		assertTrue(leaves.contains(new IntersectionNode(getNode(3))));
	}

	@Test
	public void testCreateWebEdgeOrder() throws Exception {

		Collection<IntersectionNode> leaves = executeAndGetLeaves(SegmentsBuilder.create().appendFlexible(4, 5)
				.appendFlexible(4, 3).appendFlexible(3, 2).appendFlexible(2, 1));

		assertEquals(2, leaves.size());
		Iterator<IntersectionNode> it = leaves.iterator();

		IntersectionNode node1 = it.next();
		IntersectionNode node2 = it.next();

		assertEquals(getNode(5), node1.getNode());
		assertEquals(getNode(1), node2.getNode());

		Edge edge1 = node1.getEdgesIterator().next();
		assertEquals(getNode(5), edge1.node1.getNode());
		assertEquals(getNode(4), edge1.node2.getNode());

		Edge edge2 = node2.getEdgesIterator().next();
		assertEquals(getNode(1), edge2.node1.getNode());
		assertEquals(getNode(2), edge2.node2.getNode());

	}

	@Test
	public void testCreateWebCircle() throws Exception {
		Collection<IntersectionNode> leaves = executeAndGetLeaves(SegmentsBuilder.create().appendFlexible(1, 2)
				.appendFlexible(2, 3).appendFlexible(3, 1));
		assertEquals(1, leaves.size());
	}

	@Test
	public void testCreateWebStarFlexible() throws Exception {

		Collection<IntersectionNode> leaves = executeAndGetLeaves(SegmentsBuilder.create().appendFlexible(1, 4)
				.appendFlexible(4, 5).appendFlexible(4, 6));

		assertEquals(3, leaves.size());

		assertTrue(leaves.contains(new IntersectionNode(getNode(5))));
		assertTrue(leaves.contains(new IntersectionNode(getNode(1))));
		assertTrue(leaves.contains(new IntersectionNode(getNode(6))));
	}

	@Test
	public void testCreateWebStarFixed() throws Exception {

		Collection<IntersectionNode> leaves = executeAndGetLeaves(SegmentsBuilder.create().appendFixed(1, 4)
				.appendFixed(5, 4).appendFixed(4, 6));

		assertEquals(3, leaves.size());

		assertTrue(leaves.contains(new IntersectionNode(getNode(5))));
		assertTrue(leaves.contains(new IntersectionNode(getNode(1))));
		assertTrue(leaves.contains(new IntersectionNode(getNode(6))));
	}

	@Test
	public void testCreateWebDoubleStarFlexible() throws Exception {

		Collection<IntersectionNode> leaves = executeAndGetLeaves(SegmentsBuilder.create().appendFlexible(1, 2)
				.appendFlexible(3, 2).appendFlexible(2, 4).appendFlexible(5, 4));

		assertEquals(3, leaves.size());

		assertTrue(leaves.contains(new IntersectionNode(getNode(5))));
		assertTrue(leaves.contains(new IntersectionNode(getNode(1))));
		assertTrue(leaves.contains(new IntersectionNode(getNode(3))));
	}

	@Test
	public void testCreateWebDoubleStarFixed() throws Exception {

		Collection<IntersectionNode> leaves = executeAndGetLeaves(SegmentsBuilder.create().appendFixed(1, 2)
				.appendFixed(3, 2).appendFixed(2, 4).appendFixed(5, 4));

		assertEquals(3, leaves.size());

		assertTrue(leaves.contains(new IntersectionNode(getNode(5))));
		assertTrue(leaves.contains(new IntersectionNode(getNode(1))));
		assertTrue(leaves.contains(new IntersectionNode(getNode(3))));
	}

	@Test
	public void testCreateWebDoubleLaneMixed() throws Exception {

		Collection<IntersectionNode> leaves = executeAndGetLeaves(SegmentsBuilder.create().appendFlexible(1, 2)
				.appendFixed(2, 3).appendFixed(3, 4).appendFlexible(4, 5).appendFixed(4, 6).appendFixed(6, 2));

		assertEquals(2, leaves.size());

		assertTrue(leaves.contains(new IntersectionNode(getNode(1))));
		assertTrue(leaves.contains(new IntersectionNode(getNode(5))));
	}

	@Test
	public void testCreateWebRoundabout() throws Exception {

		Collection<IntersectionNode> leaves = executeAndGetLeaves(SegmentsBuilder.create().appendFlexible(5, 4)
				.appendRoundabout(10, 5, 11, 6, 10).appendFlexible(6, 7));

		assertEquals(2, leaves.size());

		assertTrue(leaves.contains(new IntersectionNode(getNode(4))));
		assertTrue(leaves.contains(new IntersectionNode(getNode(7))));
	}

	@Test
	public void testCreateWebLineFlexible() throws Exception {

		Collection<IntersectionNode> leaves = executeAndGetLeaves(SegmentsBuilder.create().appendFlexible(1, 2, 3)
				.appendFlexible(4, 3).appendFlexible(5, 6, 1));

		assertEquals(2, leaves.size());

		assertTrue(leaves.contains(new IntersectionNode(getNode(4))));
		assertTrue(leaves.contains(new IntersectionNode(getNode(5))));
	}

	@Test
	public void testCreateWebDoubleLaneRoundabout() throws Exception {

		Collection<IntersectionNode> leaves = executeAndGetLeaves(SegmentsBuilder.create().appendFlexible(1, 2)
				.appendFixed(2, 5).appendFixed(5, 6).appendRoundabout(10, 4, 6, 7, 8, 9, 10).appendFixed(8, 12)
				.appendFixed(12, 13).appendFlexible(13, 14).appendFixed(13, 11).appendFixed(11, 9).appendFixed(4, 3)
				.appendFixed(3, 2));

		assertEquals(2, leaves.size());

		assertTrue(leaves.contains(new IntersectionNode(getNode(1))));
		assertTrue(leaves.contains(new IntersectionNode(getNode(14))));
	}

	@Test
	public void testRelation12320() throws Exception {

		Map<String, List<AggregatedSegment>> aggregatedRelation = helperService
				.loadSplittedAndAggregatedRelation(RELATION_12320_NECKARTAL_WEG);
		List<AggregatedSegment> list = aggregatedRelation.get("");

		IntersectionNodeWebCreator intersectionNodeWebCreator = new IntersectionNodeWebCreator(list.get(0)
				.getSegments());
		intersectionNodeWebCreator.createWeb();

		Collection<IntersectionNode> leaves = intersectionNodeWebCreator.getLeaves();

		assertEquals(2, leaves.size());

		helperService.exportGpx(leaves, RELATION_12320_NECKARTAL_WEG);

	}

	@Test
	public void testRelation959757() throws Exception {

		Map<String, List<AggregatedSegment>> aggregatedRelation = helperService
				.loadSplittedAndAggregatedRelation(RELATION_959757_LINE_10);
		List<AggregatedSegment> list = aggregatedRelation.get("");

		IntersectionNodeWebCreator intersectionNodeWebCreator = new IntersectionNodeWebCreator(list.get(0)
				.getSegments());
		intersectionNodeWebCreator.createWeb();

		Collection<IntersectionNode> leaves = intersectionNodeWebCreator.getLeaves();

		for (IntersectionNode intersectionNode : leaves) {
			System.out.println(intersectionNode.getNode());
		}

		assertEquals(2, leaves.size());

		assertContainsNodeId(leaves, 418151004); // Böckingen
		assertContainsNodeId(leaves, 1025039190); // Frankenbach

		helperService.exportGpx(leaves, RELATION_959757_LINE_10);
	}
}
