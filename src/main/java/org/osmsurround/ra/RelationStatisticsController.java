package org.osmsurround.ra;

import javax.validation.Valid;

import org.osmsurround.ra.analyzer.AnalyzerService;
import org.osmsurround.ra.stats.RelationStatistics;
import org.osmsurround.ra.stats.StatisticsService;
import org.osmtools.ra.RelationGoneException;
import org.osmtools.ra.context.AnalyzerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/relationStatistics")
public class RelationStatisticsController {

	@Autowired
	private StatisticsService statisticsService;
	@Autowired
	private AnalyzerService analyzerService;

	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody
	RelationStatistics get(@Valid AnalyzeRelationModel analyzeRelationModel, Errors errors) {
		try {
			if (analyzeRelationModel.getRelationId() != null) {
				AnalyzerContext analyzerContext = analyzerService.analyzeRelation(analyzeRelationModel.getRelationId(),
						analyzeRelationModel.isNoCache());

				return statisticsService.createRelationStatisticsHighway(analyzerContext.getRelation());
			}
		}
		catch (RelationGoneException e) {
		}
		return null;
	}

}
