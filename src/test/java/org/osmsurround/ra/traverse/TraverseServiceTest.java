package org.osmsurround.ra.traverse;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import org.osmsurround.ra.TestBase;
import org.osmsurround.ra.TestUtils;
import org.osmsurround.ra.context.AnalyzerContext;
import org.osmsurround.ra.data.Node;
import org.osmsurround.ra.export.Section;
import org.osmsurround.ra.export.SectionContainer;
import org.osmsurround.ra.graph.Graph;
import org.osmsurround.ra.graph.IntersectionNode;
import org.osmsurround.ra.traverse.TraverseService;
import org.springframework.beans.factory.annotation.Autowired;

public class TraverseServiceTest extends TestBase {

	@Autowired
	private TraverseService traverseService;

	@Test
	public void testTraverse() throws Exception {

		long relationId = TestUtils.RELATION_12320_NECKARTAL_WEG;

		AnalyzerContext analyzerContext = helperService.createGraphContext(relationId);

		Graph intersectionWeb = analyzerContext.getGraphs().get(0);

		Iterator<IntersectionNode> it = intersectionWeb.getLeaves().iterator();

		IntersectionNode startNode = it.next();
		IntersectionNode endNode = it.next();

		List<Node> traverse = traverseService.traverse(analyzerContext, startNode, endNode);

		List<Section> sections = new ArrayList<Section>();
		sections.add(new SectionContainer("test", traverse));

		helperService.exportSimple(sections, relationId);

	}

}
