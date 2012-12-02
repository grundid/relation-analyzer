package org.osmsurround.ra.analyzer;

import org.osmtools.ra.analyzer.AggregationService;
import org.osmtools.ra.analyzer.RelationMemberService;
import org.osmtools.ra.context.AnalyzerContext;
import org.osmtools.ra.context.AnalyzerContextService;
import org.osmtools.ra.graph.GraphService;
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
		analyzeContext(analyzerContext);
		return analyzerContext;
	}

	public void analyzeContext(AnalyzerContext analyzerContext) {
		relationMemberService.initSegments(analyzerContext);
		aggregationService.aggregate(analyzerContext);
		graphService.initGraph(analyzerContext);
	}
}
