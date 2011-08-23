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

	private List<Graph> createGraphs(List<AggregatedSegment> aggregatedSegments) {
		List<Graph> graphList = new ArrayList<Graph>();
		for (AggregatedSegment aggregatedSegment : aggregatedSegments) {
			GraphCreator graphCreator = new GraphCreator(
					aggregatedSegment.getSegments());
			graphList.add(graphCreator.createGraph());
		}
		return graphList;
	}
}
