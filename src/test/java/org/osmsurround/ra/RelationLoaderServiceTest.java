package org.osmsurround.ra;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Test;
import org.osmsurround.ra.data.Relation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

public class RelationLoaderServiceTest extends TestBase {

	@Autowired
	private RelationLoaderService relationLoaderService;
	@Autowired
	private RestTemplate restTemplate;

	@Test
	public void testLoadRelation() throws Exception {
		TestUtils.prepareRestTemplateForRelation(restTemplate, 37415);

		Relation relation = relationLoaderService.loadRelation(37415);
		assertNotNull(relation);
		assertEquals(37415, relation.getRelationId());
		assertEquals(73, relation.getMembers().size());
		assertEquals("gormur", relation.getUser());
		Calendar c = Calendar.getInstance();
		c.set(Calendar.MILLISECOND, 0);
		c.set(2011, 0, 1, 15, 42, 29);// 2011-01-01T14:42:29Z Daytime saving?
		assertEquals(c.getTimeInMillis(), relation.getTimestamp().getTimeInMillis());
	}
}
