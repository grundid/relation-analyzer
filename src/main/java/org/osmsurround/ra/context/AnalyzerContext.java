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
package org.osmsurround.ra.context;

import java.util.List;

import org.osmsurround.ra.AnalyzerException;
import org.osmsurround.ra.analyzer.AggregatedSegment;
import org.osmsurround.ra.data.Relation;
import org.osmsurround.ra.graph.Graph;
import org.osmsurround.ra.segment.ConnectableSegment;

public class AnalyzerContext {

	private Relation relation;

	private List<ConnectableSegment> segments;
	private List<AggregatedSegment> aggregatedSegments;
	private List<Graph> graphs;

	AnalyzerContext(Relation relation) {
		this.relation = relation;
	}

	public Relation getRelation() {
		return relation;
	}

	public List<ConnectableSegment> getSegments() {
		if (segments == null)
			throw new AnalyzerException("Segments not initialized");
		return segments;
	}

	public void setSegments(List<ConnectableSegment> segments) {
		this.segments = segments;
	}

	public List<AggregatedSegment> getAggregatedSegments() {
		if (aggregatedSegments == null)
			throw new AnalyzerException("Aggregated segments not initialized");
		return aggregatedSegments;
	}

	public void setAggregatedSegments(List<AggregatedSegment> aggregatedSegments) {
		this.aggregatedSegments = aggregatedSegments;
	}

	public List<Graph> getGraphs() {
		if (graphs == null)
			throw new AnalyzerException("Graphs not initialized");
		return graphs;
	}

	public void setGraphs(List<Graph> graphs) {
		this.graphs = graphs;
	}

}
