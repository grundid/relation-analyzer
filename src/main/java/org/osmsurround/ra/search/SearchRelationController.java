package org.osmsurround.ra.search;

import java.util.List;

import org.osmsurround.ra.dao.Relation;
import org.osmsurround.ra.dao.RelationSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/searchRelation")
public class SearchRelationController {

	@Autowired
	private RelationSearch relationSearch;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView get(SearchModel searchModel) {

		List<Relation> list = relationSearch.search(searchModel);

		return new ModelAndView("searchResult", "result", new SearchResult(list));
	}
}
