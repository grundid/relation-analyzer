package org.osmsurround.ra;

import org.osmsurround.ra.search.SearchModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexController {

	@RequestMapping(value = { "", "index" }, method = RequestMethod.GET)
	public String get(AnalyzeRelationModel analyzeRelationModel, SearchModel searchModel) {
		return "index";
	}
}
