package org.osmsurround.ra.map;

import org.osmsurround.ra.AnalyzeRelationModel;
import org.osmsurround.ra.analyzer.AnalyzeRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/analyzeMap")
public class AnalyzeMapController {

	@Autowired
	private AnalyzeRelationService analyzeRelationService;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView get(AnalyzeRelationModel analyzeRelationModel) {

		return new ModelAndView("analyzeMap", "report", analyzeRelationService.analyzeRelation(analyzeRelationModel));
	}

}
