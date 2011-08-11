package org.osmsurround.ra.analyzer;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Ignore;
import org.junit.Test;
import org.osmsurround.ra.HelperService;
import org.osmsurround.ra.TestBase;
import org.osmsurround.ra.TestUtils;
import org.osmsurround.ra.segment.ISegment;
import org.springframework.beans.factory.annotation.Autowired;

@SuppressWarnings("unchecked")
public class AggregationServiceTest extends TestBase {

	@Autowired
	private AggregationService aggregationService;
	@Autowired
	private HelperService helperService;

	@Test
	public void testAggregateEmpty() throws Exception {
		List<ISegment> list = aggregationService.aggregate(Collections.EMPTY_LIST);
		assertTrue(list.isEmpty());
	}

	@Test
	public void testAggregateSingle() throws Exception {
		List<ISegment> segments = Arrays.asList(TestUtils.asFixedOrderWay(1));

		List<ISegment> list = aggregationService.aggregate(segments);
		assertEquals(1, list.size());
	}

	@Test
	public void testAggregateTwoUnconnected() throws Exception {
		List<ISegment> segments = Arrays.asList(TestUtils.asFixedOrderWay(1, 2), TestUtils.asFixedOrderWay(3, 4));

		List<ISegment> list = aggregationService.aggregate(segments);
		assertEquals(2, list.size());
	}

	@Test
	public void testAggregateTwoConnected() throws Exception {
		List<ISegment> segments = Arrays.asList(TestUtils.asFixedOrderWay(1, 2), TestUtils.asFixedOrderWay(2, 3));

		List<ISegment> list = aggregationService.aggregate(segments);
		assertEquals(1, list.size());
		assertEquals(AggregatedSegment.class, list.get(0).getClass());
	}

	@Test
	public void testAggregate12320() throws Exception {
		Map<String, List<ISegment>> relation = helperService.loadSplittedRelation(TestUtils.RELATION_12320);
		List<ISegment> list = aggregationService.aggregate(relation.get(""));
		assertEquals(1, list.size());
	}

	@Test
	@Ignore
	public void testAggregate37415() throws Exception {
		Map<String, List<ISegment>> relation = helperService.loadSplittedRelation(TestUtils.RELATION_37415);
		for (Entry<String, List<ISegment>> entry : relation.entrySet()) {
			List<ISegment> list = aggregationService.aggregate(entry.getValue());
			assertEquals(1, list.size());
		}
	}

	@Test
	public void testAggregate959757() throws Exception {
		Map<String, List<ISegment>> relation = helperService.loadSplittedRelation(TestUtils.RELATION_959757);
		for (Entry<String, List<ISegment>> entry : relation.entrySet()) {
			List<ISegment> list = aggregationService.aggregate(entry.getValue());
			assertEquals(1, list.size());
		}
	}

	@Test
	@Ignore
	public void testAggregate954995() throws Exception {
		Map<String, List<ISegment>> relation = helperService.loadSplittedRelation(954995);
		for (Entry<String, List<ISegment>> entry : relation.entrySet()) {
			List<ISegment> list = aggregationService.aggregate(entry.getValue());
			assertEquals(1, list.size());
		}
	}
}
