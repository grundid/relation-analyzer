package org.osmsurround.ra.analyzer;

import org.osmsurround.ra.AnalyzeRelationModel;
import org.osmsurround.ra.RelationLoaderService;
import org.osmsurround.ra.data.Relation;
import org.osmsurround.ra.report.AnalyzerReport;
import org.osmsurround.ra.report.AnalyzerReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnalyzeRelationService {

	@Autowired
	private RelationLoaderService relationLoaderService;
	@Autowired
	private ConnectableService aggregationService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private AnalyzerReportService analyzerReportService;

	public AnalyzerReport createReport(AnalyzeRelationModel relationModel) {
		Relation osmRelation = relationLoaderService.loadRelation(relationModel.getRelationId().longValue());

		//		Map<String, List<ISegment>> aggregatedRelation = new HashMap<String, List<ISegment>>();
		//
		//		Map<String, List<ISegment>> splittedRelation = roleService.splitRelationByRole(osmRelation);
		//		for (Entry<String, List<ISegment>> entry : splittedRelation.entrySet()) {
		//			List<ISegment> list = aggregationService.aggregate(entry.getValue());
		//			aggregatedRelation.put(entry.getKey(), list);
		//		}

		//		AnalyzerReport analyzerReport = analyzerReportService.createReport(aggregatedRelation);
		//		return analyzerReport;
		return null;
	}

}
