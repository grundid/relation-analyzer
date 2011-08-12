package org.osmsurround.ra.analyzer;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;
import org.osmsurround.ra.HelperService;
import org.osmsurround.ra.TestBase;
import org.osmsurround.ra.TestUtils;
import org.osmsurround.ra.segment.ISegment;
import org.springframework.beans.factory.annotation.Autowired;

public class IntersectionNodeWebCreatorTest extends TestBase {

	@Autowired
	private HelperService helperService;

	@Test
	public void testCreateWebStarFixed() throws Exception {

		List<ISegment> segments = new ArrayList<ISegment>();
		segments.add(TestUtils.asFixedOrderWay(1, 2, 3, 4));
		segments.add(TestUtils.asFixedOrderWay(4, 5, 6, 7));
		segments.add(TestUtils.asFixedOrderWay(4, 8, 9, 10));

		IntersectionNodeWebCreator intersectionNodeWebCreator = new IntersectionNodeWebCreator(segments);
		IntersectionNode intersectionNode = intersectionNodeWebCreator.createWebStartingWithNode(TestUtils.getNode(4));

		Iterator<Edge> iterator = intersectionNode.getEdgesIterator();

		assertTrue(iterator.hasNext());
		assertNotNull(iterator.next());
		assertNotNull(iterator.next());
		assertNotNull(iterator.next());
	}

	@Test
	public void testCreateWebStarFlexible() throws Exception {

		List<ISegment> segments = new ArrayList<ISegment>();
		segments.add(TestUtils.asFlexibleOrderWay(1, 2, 3));
		segments.add(TestUtils.asFlexibleOrderWay(4, 3));
		segments.add(TestUtils.asFlexibleOrderWay(5, 6, 1));

		IntersectionNodeWebCreator intersectionNodeWebCreator = new IntersectionNodeWebCreator(segments);
		IntersectionNode intersectionNode = intersectionNodeWebCreator.createWeb();

		Iterator<Edge> iterator = intersectionNode.getEdgesIterator();

		assertTrue(iterator.hasNext());
	}

	@Test
	@Ignore
	public void testRelation12320() throws Exception {
		Map<String, List<AggregatedSegment>> aggregatedRelation = helperService
				.loadSplittedAndAggregatedRelation(12320);
		List<AggregatedSegment> list = aggregatedRelation.get("");

		IntersectionNodeWebCreator intersectionNodeWebCreator = new IntersectionNodeWebCreator(list.get(0)
				.getSegments());
		IntersectionNode intersectionNode = intersectionNodeWebCreator.createWeb();

	}
}
