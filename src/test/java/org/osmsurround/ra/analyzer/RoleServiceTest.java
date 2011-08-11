package org.osmsurround.ra.analyzer;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.osmsurround.ra.RelationLoaderService;
import org.osmsurround.ra.TestBase;
import org.osmsurround.ra.TestUtils;
import org.osmsurround.ra.data.Relation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

public class RoleServiceTest extends TestBase {

	@Autowired
	private RoleService roleService;
	@Autowired
	private RelationLoaderService relationLoaderService;
	@Autowired
	private RestTemplate restTemplate;

	@Test
	public void testSplitRelationByRole() throws Exception {
		TestUtils.prepareRestTemplateForRelation(restTemplate, 37415);

		Relation osmRelation = relationLoaderService.loadRelation(37415);

		Map<String, List<ISegment>> splittedRelations = roleService.splitRelationByRole(osmRelation);
		assertNotNull(splittedRelations);

		assertEquals(1, splittedRelations.size());
		assertTrue(splittedRelations.containsKey(""));
		assertEquals(73, splittedRelations.get("").size());
	}

	@Test
	public void testSplitRelationByRoleEmpty() throws Exception {
		TestUtils.prepareRestTemplateForRelation(restTemplate, 12320);

		Relation osmRelation = relationLoaderService.loadRelation(12320);

		Map<String, List<ISegment>> splittedRelations = roleService.splitRelationByRole(osmRelation);
		assertNotNull(splittedRelations);

		assertEquals(1, splittedRelations.size());
		assertTrue(splittedRelations.containsKey(""));

		assertEquals(931, splittedRelations.get("").size());
		assertEquals(5, countInstances(FlexibleRoundaboutWay.class, splittedRelations.get("")));
		assertEquals(926, countInstances(FlexibleOrderWay.class, splittedRelations.get("")));
	}

	private int countInstances(Class<?> classToCount, List<ISegment> segments) {
		int instances = 0;
		for (ISegment segment : segments) {
			if (segment.getClass().getName().equals(classToCount.getName()))
				instances++;
		}
		return instances;
	}

}
