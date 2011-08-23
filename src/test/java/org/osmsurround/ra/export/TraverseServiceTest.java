package org.osmsurround.ra.export;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import org.osmsurround.ra.TestBase;
import org.osmsurround.ra.TestUtils;
import org.osmsurround.ra.context.AnalyzerContext;
import org.osmsurround.ra.data.Node;
import org.osmsurround.ra.web.IntersectionNode;
import org.osmsurround.ra.web.IntersectionWeb;
import org.springframework.beans.factory.annotation.Autowired;

public class TraverseServiceTest extends TestBase {

	@Autowired
	private TraverseService traverseService;

	@Test
	public void testTraverse() throws Exception {

		long relationId = TestUtils.RELATION_959757_LINE_10;

		AnalyzerContext analyzerContext = helperService.createIntersectionWebContext(relationId);

		IntersectionWeb intersectionWeb = analyzerContext.getGraphs().get(0);

		Iterator<IntersectionNode> it = intersectionWeb.getLeaves().iterator();

		IntersectionNode startNode = it.next();
		IntersectionNode endNode = it.next();

		List<Node> traverse = traverseService.traverse(analyzerContext, startNode, endNode);

		List<Section> sections = new ArrayList<Section>();
		sections.add(new SectionContainer("test", traverse));

		helperService.exportSimple(sections, relationId);

	}

}
