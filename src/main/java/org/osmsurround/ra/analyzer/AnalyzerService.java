package org.osmsurround.ra.analyzer;

import org.osmsurround.ra.context.AnalyzerContext;
import org.osmsurround.ra.context.AnalyzerContextService;
import org.osmsurround.ra.graph.GraphService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnalyzerService {

	@Autowired
	private RelationMemberService relationMemberService;
	@Autowired
	private AnalyzerContextService analyzerContextService;
	@Autowired
	private AggregationService aggregationService;
	@Autowired
	private GraphService graphService;

	public AnalyzerContext analyzeRelation(long relationId, boolean noCache) {
		AnalyzerContext analyzerContext = analyzerContextService.createAnalyzerContext(relationId, noCache);

		relationMemberService.initSegments(analyzerContext);
		aggregationService.aggregate(analyzerContext);
		graphService.initGraph(analyzerContext);

		return analyzerContext;
	}
}
