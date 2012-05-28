package org.osmsurround.ra.dijkstra;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import org.osmsurround.ra.TestBase;
import org.osmsurround.ra.TestUtils;
import org.osmsurround.ra.context.AnalyzerContext;
import org.osmsurround.ra.graph.Graph;
import org.osmsurround.ra.graph.IntersectionNode;

public class DijkstraTest extends TestBase {

	@Test
	public void testRelation12320() throws Exception {

		AnalyzerContext analyzerContext = helperService.createGraphContext(TestUtils.RELATION_12320_NECKARTAL_WEG);

		// We know this relation. Only two end nodes available.
		Graph intersectionWeb = analyzerContext.getGraphs().get(0);
		Iterator<IntersectionNode> it = intersectionWeb.getLeaves().iterator();
		IntersectionNode startNode = it.next();
		IntersectionNode endNode = it.next();

		// Init dijkstra with the edges
		Dijkstra dijkstraAlgorithm = new Dijkstra(intersectionWeb.getEdges());

		dijkstraAlgorithm.execute(startNode);

		// get the Path to endNode
		List<Vertex> path = dijkstraAlgorithm.getPath(endNode);

		assertEquals(startNode, path.get(0));
		assertEquals(endNode, path.get(path.size() - 1));

	}

}
