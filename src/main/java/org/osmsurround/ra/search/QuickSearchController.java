package org.osmsurround.ra.search;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/quickSearch")
public class QuickSearchController {

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView get(String query) {
		RedirectView view = null;
		try {
			long relationId = Long.parseLong(query);
			view = new RedirectView("analyzeRelation?relationId=" + relationId, true);
		}
		catch (NumberFormatException e) {
			view = new RedirectView("searchRelation?name=" + query, true);
		}
		view.setEncodingScheme("utf8");
		return new ModelAndView(view);
	}
}
