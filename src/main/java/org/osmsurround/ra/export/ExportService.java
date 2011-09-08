package org.osmsurround.ra.export;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.osmsurround.ra.context.AnalyzerContext;
import org.osmsurround.ra.data.Node;
import org.osmsurround.ra.graph.Graph;
import org.osmsurround.ra.graph.IntersectionNode;
import org.osmsurround.ra.traverse.TraverseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExportService {

	@Autowired
	private TraverseService traverseService;
	@Autowired
	private GpxExport gpxExport;

	public void export(AnalyzerContext analyzerContext, OutputStream out) {

		List<Section> containers = new ArrayList<Section>();
		for (Graph graph : analyzerContext.getGraphs()) {

			IntersectionNode[] intersectionNodes = graph.getLeaves().toArray(new IntersectionNode[0]);
			if (intersectionNodes.length == 2) {
				List<Node> list = traverseService.traverse(intersectionNodes[0], intersectionNodes[1]);
				List<Node> completeNodeList = traverseService.fillInNodes(list, analyzerContext);
				SectionContainer sectionContainer = new SectionContainer("Graph X", completeNodeList);
				containers.add(sectionContainer);
			}
		}

		gpxExport.export(containers, out);

	}
}
