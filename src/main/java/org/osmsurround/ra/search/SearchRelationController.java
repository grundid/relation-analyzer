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
package org.osmsurround.ra.search;

import java.util.Collections;
import java.util.List;

import javax.validation.Valid;

import org.osmsurround.ra.dao.Relation;
import org.osmsurround.ra.dao.RelationSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/searchRelation")
public class SearchRelationController {

	@Autowired
	private RelationSearch relationSearch;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView get(@Valid SearchModel searchModel, Errors errors) {

		if (errors.hasErrors()) {
			return new ModelAndView("searchResult", "result", new SearchResult(Collections.EMPTY_LIST));
		}
		else {
			List<Relation> list = relationSearch.search(searchModel);
			return new ModelAndView("searchResult", "result", new SearchResult(list));
		}
	}
}
