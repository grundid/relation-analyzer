package org.osmsurround.ra.analyzer;

import org.osmsurround.ra.AnalyzeRelationModel;
import org.osmsurround.ra.context.AnalyzerContext;
import org.osmsurround.ra.context.AnalyzerContextService;
import org.osmsurround.ra.graph.GraphService;
import org.osmsurround.ra.report.Report;
import org.osmsurround.ra.report.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnalyzeRelationService {

	@Autowired
	private RelationMemberService relationMemberService;
	@Autowired
	private AnalyzerContextService analyzerContextService;
	@Autowired
	private AggregationService aggregationService;
	@Autowired
	private GraphService graphService;
	@Autowired
	private ReportService reportService;

	public Report analyzeRelation(AnalyzeRelationModel analyzeRelationModel) {

		AnalyzerContext analyzerContext = analyzerContextService.createAnalyzerContext(analyzeRelationModel
				.getRelationId().longValue());

		relationMemberService.initSegments(analyzerContext);
		aggregationService.aggregate(analyzerContext);
		graphService.initGraph(analyzerContext);

		return reportService.generateReport(analyzerContext);
	}
}
