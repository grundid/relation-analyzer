package org.osmsurround.ra.report;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.osmsurround.ra.context.AnalyzerContext;
import org.osmsurround.ra.data.Node;
import org.osmsurround.ra.graph.Graph;
import org.osmsurround.ra.graph.IntersectionNode;
import org.osmsurround.ra.utils.LonLatMath;
import org.springframework.stereotype.Service;

@Service
public class ReportService {

	private static final String RELATION_IN_ONE_PIECE = "relation.in.one.piece";
	private static final String RELATION_DISCONNECTED = "relation.disconnect";
	private static final int MAX_DISTANCES = 3;

	public Report generateReport(AnalyzerContext analyzerContext) {
		Report report = new Report();

		initReportItems(report, analyzerContext);
		initRating(report, analyzerContext);

		return report;
	}

	private void initReportItems(Report report, AnalyzerContext analyzerContext) {

		List<ReportItem> reportItems = new ArrayList<ReportItem>();
		List<Graph> graphs = analyzerContext.getGraphs();

		for (Graph graph : graphs) {
			Set<IntersectionNode> leaves = graph.getLeaves();

			Map<Node, Collection<NodeDistance>> nodes = new HashMap<Node, Collection<NodeDistance>>();

			for (IntersectionNode intersectionNode : leaves) {
				Node node = intersectionNode.getNode();
				Set<NodeDistance> distances = getDistances(graphs, node);
				filterDistances(distances);

				nodes.put(node, distances);
			}

			reportItems.add(new ReportItem(nodes));
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
		Set<NodeDistance> distances = new TreeSet<NodeDistance>(new NodeDistanceComparator());
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

	private void initRating(Report report, AnalyzerContext analyzerContext) {
		String relationType = analyzerContext.getRelation().getTags().get("type");
		if (relationType.equals("route")) {
			if (analyzerContext.getGraphs().size() == 1) {
				report.setRating(Rating.OK);
				report.setMessageCode(RELATION_IN_ONE_PIECE);
			}
			else {
				report.setRating(Rating.DISCONNECTED);
				report.setMessageCode(RELATION_DISCONNECTED);
			}
		}
	}

}
