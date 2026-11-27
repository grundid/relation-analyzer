package org.osmsurround.ra;

import org.osmsurround.ra.analyzer.AnalyzerService;
import org.osmtools.ra.analyzer.AggregationService;
import org.osmtools.ra.context.AnalyzerContext;
import org.osmtools.ra.context.AnalyzerContextService;
import org.osmtools.ra.graph.GraphService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class HelperService {

	@Autowired
	private AnalyzerService analyzerService;
	@Autowired
	private AnalyzerContextService analyzerContextService;
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private AggregationService aggregationService;
	@Autowired
	private GraphService graphService;

	public AnalyzerContext createInitializedContext(long relationId) {
		TestUtils.prepareRestTemplateForRelation(restTemplate, relationId);
		AnalyzerContext analyzerContext = analyzerContextService.createAnalyzerContext(relationId, false);
		analyzerService.initSegments(analyzerContext);
		return analyzerContext;
	}

	public AnalyzerContext createAggregatedContext(long relationId) {
		AnalyzerContext analyzerContext = createInitializedContext(relationId);
		aggregationService.aggregate(analyzerContext);
		return analyzerContext;
	}

	public AnalyzerContext createGraphContext(long relationId) {
		AnalyzerContext analyzerContext = createAggregatedContext(relationId);
		graphService.initGraph(analyzerContext);
		return analyzerContext;
	}
}
