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
package org.osmsurround.ra;

import javax.validation.Valid;

import org.osmsurround.ra.analyzer.AnalyzeRelationService;
import org.osmsurround.ra.report.Report;
import org.osmtools.ra.RelationGoneException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/analyzeRelation")
public class AnalyzeRelationController {

	@Autowired
	private AnalyzeRelationService analyzeRelationService;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView get(@Valid AnalyzeRelationModel analyzeRelationModel, Errors errors) {
		try {
			return new ModelAndView("analyzeResult", "report",
					analyzeRelationService.analyzeRelation(analyzeRelationModel));
		}
		catch (RelationGoneException e) {
			return new ModelAndView("analyzeResult", "report", new Report(true));
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/json")
	public @ResponseBody Report getJson(@Valid AnalyzeRelationModel analyzeRelationModel, Errors errors) {
		try {
			return analyzeRelationService.analyzeRelation(analyzeRelationModel);
		}
		catch (RelationGoneException e) {
			return new Report(true);
		}
	}
}
