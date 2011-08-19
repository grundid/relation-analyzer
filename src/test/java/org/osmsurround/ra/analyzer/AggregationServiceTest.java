package org.osmsurround.ra.analyzer;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.osmsurround.ra.HelperService;
import org.osmsurround.ra.TestBase;
import org.osmsurround.ra.TestUtils;
import org.osmsurround.ra.context.AnalyzerContext;
import org.osmsurround.ra.segment.ISegment;
import org.springframework.beans.factory.annotation.Autowired;

@SuppressWarnings("unchecked")
public class AggregationServiceTest extends TestBase {

	@Autowired
	private AggregationService aggregationService;
	@Autowired
	private HelperService helperService;

	@Test
	public void testAggregateSegmentsEmpty() throws Exception {
		List<AggregatedSegment> list = aggregationService.aggregateSegments(Collections.EMPTY_LIST);
		assertTrue(list.isEmpty());
	}

	@Test
	public void testAggregateSegmentsSingle() throws Exception {
		List<ISegment> segments = Arrays.asList(TestUtils.asFixedOrderWay(1));

		List<AggregatedSegment> list = aggregationService.aggregateSegments(segments);
		assertEquals(1, list.size());
	}

	@Test
	public void testAggregateSegmentsTwoUnconnected() throws Exception {
		List<ISegment> segments = Arrays.asList(TestUtils.asFixedOrderWay(1, 2), TestUtils.asFixedOrderWay(3, 4));

		List<AggregatedSegment> list = aggregationService.aggregateSegments(segments);
		assertEquals(2, list.size());
	}

	@Test
	public void testAggregateSegmentsTwoConnected() throws Exception {
		List<ISegment> segments = Arrays.asList(TestUtils.asFixedOrderWay(1, 2), TestUtils.asFixedOrderWay(2, 3));

		List<AggregatedSegment> list = aggregationService.aggregateSegments(segments);
		assertEquals(1, list.size());
		assertEquals(AggregatedSegment.class, list.get(0).getClass());
	}

	private static void assertAggregatedSegments(List<AggregatedSegment> aggregatedSegments,
			int expectedAggregatedSegemnts, int... segmentsPerEntry) {
		assertEquals(expectedAggregatedSegemnts, aggregatedSegments.size());
		for (int x = 0; x < expectedAggregatedSegemnts; x++) {
			assertEquals(segmentsPerEntry[x], aggregatedSegments.get(x).getSegments().size());
		}
	}

	@Test
	public void testAggregate12320() throws Exception {
		AnalyzerContext analyzerContext = helperService
				.createInitializedContext(TestUtils.RELATION_12320_NECKARTAL_WEG);
		aggregationService.aggregate(analyzerContext);
		assertAggregatedSegments(analyzerContext.getAggregatedSegments(), 1, 931);
	}

	@Test
	public void testAggregate37415() throws Exception {
		AnalyzerContext analyzerContext = helperService.createInitializedContext(TestUtils.RELATION_37415);
		aggregationService.aggregate(analyzerContext);
		assertAggregatedSegments(analyzerContext.getAggregatedSegments(), 4, 35, 34, 3, 1);
	}

	@Test
	public void testAggregate959757() throws Exception {
		AnalyzerContext analyzerContext = helperService.createInitializedContext(TestUtils.RELATION_959757_LINE_10);
		aggregationService.aggregate(analyzerContext);
		assertAggregatedSegments(analyzerContext.getAggregatedSegments(), 1, 111);
	}

	@Test
	public void testAggregate954995() throws Exception {
		AnalyzerContext analyzerContext = helperService.createInitializedContext(954995);
		aggregationService.aggregate(analyzerContext);
		assertAggregatedSegments(analyzerContext.getAggregatedSegments(), 3, 43, 7, 1);
	}
}
