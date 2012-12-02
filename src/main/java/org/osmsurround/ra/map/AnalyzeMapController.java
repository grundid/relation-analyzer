package org.osmsurround.ra.map;

import javax.validation.Valid;

import org.osmsurround.ra.AnalyzeRelationModel;
import org.osmsurround.ra.analyzer.AnalyzeRelationService;
import org.osmsurround.ra.report.Report;
import org.osmtools.ra.RelationGoneException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/analyzeMap")
public class AnalyzeMapController {

	@Autowired
	private AnalyzeRelationService analyzeRelationService;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView get(@Valid AnalyzeRelationModel analyzeRelationModel, Errors errors) {
		try {
			return new ModelAndView("analyzeMap", "report",
					analyzeRelationService.analyzeRelation(analyzeRelationModel));
		}
		catch (RelationGoneException e) {
			return new ModelAndView("analyzeResult", "report", new Report(true));
		}
	}

}
