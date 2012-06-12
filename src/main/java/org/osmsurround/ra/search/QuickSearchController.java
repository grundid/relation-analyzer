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

		try {
			long relationId = Long.parseLong(query);
			return new ModelAndView(new RedirectView("analyzeRelation?relationId=" + relationId, true));
		}
		catch (NumberFormatException e) {
			return new ModelAndView(new RedirectView("searchRelation?name=" + query, true));
		}
	}

}
