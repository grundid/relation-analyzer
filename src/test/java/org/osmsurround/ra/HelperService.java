package org.osmsurround.ra;

import java.util.List;
import java.util.Map;

import org.osmsurround.ra.analyzer.AggregationService;
import org.osmsurround.ra.analyzer.RoleService;
import org.osmsurround.ra.data.Relation;
import org.osmsurround.ra.segment.ISegment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class HelperService {

	@Autowired
	private RoleService roleService;
	@Autowired
	private RelationLoaderService relationLoaderService;
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private AggregationService aggregationService;

	public Map<String, List<ISegment>> loadSplittedRelation(long relationId) {
		TestUtils.prepareRestTemplateForRelation(restTemplate, relationId);
		Relation osmRelation = relationLoaderService.loadRelation(relationId);
		return roleService.splitRelationByRole(osmRelation);
	}

	//	public Map<String, List<ISegment>> loadSplittedAndAggregatedRelation(long relationId) {
	//		Map<String, List<ISegment>> aggregatedRelation = new HashMap<String, List<ISegment>>();
	//		Map<String, List<ISegment>> splittedRelation = loadSplittedRelation(relationId);
	//		for (Entry<String, List<ISegment>> entry : splittedRelation.entrySet()) {
	//			List<ISegment> list = aggregationService.aggregate(entry.getValue());
	//			aggregatedRelation.put(entry.getKey(), list);
	//		}
	//		return aggregatedRelation;
	//	}
}
