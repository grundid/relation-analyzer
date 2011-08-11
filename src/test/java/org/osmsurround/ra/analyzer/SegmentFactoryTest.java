package org.osmsurround.ra.analyzer;

import static org.junit.Assert.*;

import org.junit.Test;
import org.osmsurround.ra.TestBase;
import org.osmsurround.ra.TestUtils;
import org.osmsurround.ra.data.Member;
import org.springframework.beans.factory.annotation.Autowired;

public class SegmentFactoryTest extends TestBase {

	@Autowired
	private SegmentFactory segmentFactory;

	@Test
	public void testCreateMember() throws Exception {

		assertTrue(segmentFactory.createSegment(new Member(TestUtils.asWay(1, 2, 3), "")) instanceof FlexibleOrderWay);
		assertTrue(segmentFactory.createSegment(new Member(TestUtils.asWay(1, 2, 3), "forward")) instanceof FixedOrderWay);
		assertTrue(segmentFactory.createSegment(new Member(TestUtils.asWay(1, 2, 3), "backward")) instanceof FixedOrderWay);
		assertTrue(segmentFactory.createSegment(new Member(TestUtils.asWay(10, 11, 12, 13, 10), "")) instanceof FlexibleRoundaboutWay);
		assertTrue(segmentFactory.createSegment(new Member(TestUtils.asWay(10, 11, 12, 13, 10), "forward")) instanceof FixedRoundaboutWay);
		assertTrue(segmentFactory.createSegment(new Member(TestUtils.asWay(10, 11, 12, 13, 10), "backward")) instanceof FixedRoundaboutWay);

	}
}
