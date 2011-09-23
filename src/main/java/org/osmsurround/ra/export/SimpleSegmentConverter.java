package org.osmsurround.ra.export;

import java.util.ArrayList;
import java.util.List;

import org.osmsurround.ra.context.AnalyzerContext;
import org.osmsurround.ra.data.Node;
import org.osmsurround.ra.dijkstra.Edge;
import org.osmsurround.ra.graph.Graph;
import org.osmsurround.ra.segment.ConnectableSegment;

public class SimpleSegmentConverter {

	public List<Section> convert(AnalyzerContext analyzerContext) {

		List<Section> containers = new ArrayList<Section>();

		List<Graph> graphs = analyzerContext.getGraphs();

		for (int graphIndex = 0; graphIndex < graphs.size(); graphIndex++) {

			Graph graph = graphs.get(graphIndex);

			SectionContainer sectionContainer = new SectionContainer("Graph " + (graphIndex + 1));

			for (Edge edge : graph.getEdges()) {
				Node startNode = edge.getSource().getNode();
				Node currentNode = edge.getDestination().getNode();

				List<Node> nodes = new ArrayList<Node>();
				nodes.add(startNode);
				for (ConnectableSegment connectableSegment : analyzerContext.getSegments()) {
					if (connectableSegment.containsNodes(startNode, currentNode)) {
						int prevSize = nodes.size();
						connectableSegment.appendNodesBetween(nodes, startNode, currentNode);
						if (prevSize < nodes.size())
							break;
					}
				}
				sectionContainer.addCoordinates(nodes);
			}
			containers.add(sectionContainer);
		}

		return containers;
	}

}
