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
package org.osmsurround.ra.report;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import org.osmsurround.ra.context.AnalyzerContext;
import org.osmsurround.ra.data.Node;
import org.osmsurround.ra.data.Relation;
import org.osmsurround.ra.graph.Graph;
import org.osmsurround.ra.graph.IntersectionNode;
import org.osmsurround.ra.utils.LonLatMath;
import org.springframework.stereotype.Service;

@Service
public class ReportService {

	private static final NodeDistanceComparator NODE_DISTANCE_COMPARATOR = new NodeDistanceComparator();
	private static final String RELATION_IN_ONE_PIECE = "relation.in.one.piece";
	private static final String RELATION_DISCONNECTED = "relation.disconnect";
	private static final String RELATION_NO_RATING = "relation.no.rating";

	private static final int MAX_DISTANCES = 3;

	public Report generateReport(AnalyzerContext analyzerContext) {
		Report report = new Report();

		initReportItems(report, analyzerContext);
		initRelationRating(report, analyzerContext);
		initRelationInfo(report, analyzerContext);

		return report;
	}

	private void initRelationInfo(Report report, AnalyzerContext analyzerContext) {

		Relation relation = analyzerContext.getRelation();

		RelationInfo relationInfo = new RelationInfo();
		relationInfo.setRelationId(relation.getRelationId());
		relationInfo.setTimestamp(relation.getTimestamp());
		relationInfo.setUser(relation.getUser());
		relationInfo.setName(relation.getTags().get("name"));
		relationInfo.setType(relation.getTags().get("type"));

		List<RelationTag> tags = new ArrayList<RelationTag>();
		for (Entry<String, String> entry : relation.getTags().entrySet()) {
			tags.add(new RelationTag(entry.getKey(), entry.getValue()));
		}
		relationInfo.setTags(tags);

		report.setRelationInfo(relationInfo);

	}

	private void initReportItems(Report report, AnalyzerContext analyzerContext) {

		List<ReportItem> reportItems = new ArrayList<ReportItem>();
		List<Graph> graphs = analyzerContext.getGraphs();

		for (Graph graph : graphs) {
			Set<IntersectionNode> leaves = graph.getLeaves();

			List<EndNodeDistances> endNodeDistances = new ArrayList<EndNodeDistances>();

			for (IntersectionNode intersectionNode : leaves) {
				Node node = intersectionNode.getNode();
				Set<NodeDistance> distances = getDistances(graphs, node);
				filterDistances(distances);

				endNodeDistances.add(new EndNodeDistances(node, distances));
			}

			reportItems.add(new ReportItem(endNodeDistances));
		}

		report.setReportItems(reportItems);
	}

	private void filterDistances(Set<NodeDistance> distances) {
		int count = 0;
		for (Iterator<NodeDistance> it = distances.iterator(); it.hasNext();) {
			it.next();
			count++;
			if (count > MAX_DISTANCES)
				it.remove();
		}
	}

	private Set<NodeDistance> getDistances(List<Graph> graphs, Node distantNode) {
		Set<NodeDistance> distances = new TreeSet<NodeDistance>(NODE_DISTANCE_COMPARATOR);
		for (Graph graph : graphs) {
			Set<IntersectionNode> leaves = graph.getLeaves();

			for (IntersectionNode intersectionNode : leaves) {
				Node node = intersectionNode.getNode();
				if (distantNode != node) {
					double distance = Math.abs(LonLatMath.distance(node.getLon(), node.getLat(), distantNode.getLon(),
							distantNode.getLat()));
					distances.add(new NodeDistance(node, distance));
				}
			}

		}
		return distances;
	}

	private void initRelationRating(Report report, AnalyzerContext analyzerContext) {
		String relationType = analyzerContext.getRelation().getTags().get("type");
		if (relationType.equals("route")) {
			if (analyzerContext.getGraphs().size() == 1) {
				report.setRelationRating(new RelationRating(Rating.OK, RELATION_IN_ONE_PIECE));
			}
			else {
				report.setRelationRating(new RelationRating(Rating.DISCONNECTED, RELATION_DISCONNECTED));
			}
		}
		else {
			report.setRelationRating(new RelationRating(Rating.UNKNOWN, RELATION_NO_RATING));
		}
	}

}
