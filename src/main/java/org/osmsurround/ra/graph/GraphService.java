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
package org.osmsurround.ra.graph;

import java.util.ArrayList;
import java.util.List;

import org.osmsurround.ra.analyzer.AggregatedSegment;
import org.osmsurround.ra.context.AnalyzerContext;
import org.springframework.stereotype.Service;

@Service
public class GraphService {

	public void initGraph(AnalyzerContext analyzerContext) {
		List<AggregatedSegment> aggregatedSegments = analyzerContext.getAggregatedSegments();
		List<Graph> graphList = createGraphs(aggregatedSegments);
		analyzerContext.setGraphs(graphList);
	}

	public List<Graph> createGraphs(List<AggregatedSegment> aggregatedSegments) {
		List<Graph> graphList = new ArrayList<Graph>();
		for (AggregatedSegment aggregatedSegment : aggregatedSegments) {
			GraphCreator graphCreator = new GraphCreator(aggregatedSegment.getSegments());
			graphList.add(graphCreator.getGraph());
		}
		return graphList;
	}
}
