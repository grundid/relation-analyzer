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
		ModelAndView mav = new ModelAndView();
		RedirectView view = null;
		try {
			Long.parseLong(query);
			view = new RedirectView("analyzeRelation", true);
			mav.addObject("relationId", query);
		}
		catch (NumberFormatException e) {
			view = new RedirectView("searchRelation", true);
			mav.addObject("name", query);
		}
		view.setEncodingScheme("utf8");
		mav.setView(view);
		return mav;
	}
}
