package org.osmsurround.ra.context;

import org.osmsurround.ra.RelationLoaderService;
import org.osmsurround.ra.data.Relation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnalyzerContextService {

	@Autowired
	private RelationLoaderService relationLoaderService;

	public AnalyzerContext createAnalyzerContext(long relationId, boolean reload) {
		Relation relation = reload ? relationLoaderService.reloadRelation(relationId) : relationLoaderService
				.loadRelation(relationId);
		return new AnalyzerContext(relation);
	}
}
