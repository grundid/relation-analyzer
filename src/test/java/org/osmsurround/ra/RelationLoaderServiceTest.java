package org.osmsurround.ra;

import static org.junit.Assert.*;

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

		Relation osmRelation = relationLoaderService.loadRelation(37415);
		assertNotNull(osmRelation);
		assertEquals(37415, osmRelation.getRelationId());
		assertEquals(73, osmRelation.getMembers().size());
	}
}
