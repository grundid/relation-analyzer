/**
 * This file is part of Relation Analyzer for OSM.
 * Copyright (c) 2001 by Adrian Stabiszewski, as@grundid.de
 *
 * Relation Analyzer is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Relation Analyzer is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with Relation Analyzer.  If not, see <http://www.gnu.org/licenses/>.
 */
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
				.getRelationId().longValue(), analyzeRelationModel.isNoCache());

		relationMemberService.initSegments(analyzerContext);
		aggregationService.aggregate(analyzerContext);
		graphService.initGraph(analyzerContext);

		Report report = reportService.generateReport(analyzerContext);

		return report;
	}
}
