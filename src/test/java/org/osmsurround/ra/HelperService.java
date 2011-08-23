package org.osmsurround.ra;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.osmsurround.ra.analyzer.AggregationService;
import org.osmsurround.ra.analyzer.RelationMemberService;
import org.osmsurround.ra.context.AnalyzerContext;
import org.osmsurround.ra.context.AnalyzerContextService;
import org.osmsurround.ra.data.Node;
import org.osmsurround.ra.export.GpxExport;
import org.osmsurround.ra.export.Section;
import org.osmsurround.ra.export.SectionContainer;
import org.osmsurround.ra.export.TraverseService;
import org.osmsurround.ra.graph.GraphService;
import org.osmsurround.ra.graph.IntersectionNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class HelperService {

	@Autowired
	private RelationMemberService relationMemberService;
	@Autowired
	private AnalyzerContextService analyzerContextService;
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private AggregationService aggregationService;
	@Autowired
	private TraverseService traverseService;
	@Autowired
	private GpxExport gpxExport;
	@Autowired
	private GraphService intersectionWebService;

	public AnalyzerContext createInitializedContext(long relationId) {
		TestUtils.prepareRestTemplateForRelation(restTemplate, relationId);
		AnalyzerContext analyzerContext = analyzerContextService.createAnalyzerContext(relationId);
		relationMemberService.initSegments(analyzerContext);
		return analyzerContext;
	}

	public AnalyzerContext createAggregatedContext(long relationId) {
		AnalyzerContext analyzerContext = createInitializedContext(relationId);
		aggregationService.aggregate(analyzerContext);
		return analyzerContext;
	}

	public AnalyzerContext createIntersectionWebContext(long relationId) {
		AnalyzerContext analyzerContext = createAggregatedContext(relationId);
		intersectionWebService.initGraph(analyzerContext);
		return analyzerContext;
	}

	@Deprecated
	public void exportGpx(Collection<IntersectionNode> leaves, long relationId) {

		List<Section> sectionList = new ArrayList<Section>();

		for (IntersectionNode startNode : leaves) {
			for (IntersectionNode endNode : leaves) {
				if (startNode != endNode) {
					try {
						List<Node> traverse = traverseService.traverse(startNode, endNode);

						sectionList.add(new SectionContainer("Route - " + startNode.getNode() + " - "
								+ endNode.getNode(), traverse));
					}
					catch (AnalyzerException e) {
						System.out.println("cannot create route - " + startNode.getNode() + " - " + endNode.getNode());
					}
				}
			}
		}

		exportSimple(sectionList, relationId);

	}

	public void exportSimple(List<Section> sectionList, long relationId) {
		try {
			FileOutputStream fos = new FileOutputStream("c:\\test_" + relationId + ".gpx");

			gpxExport.export(sectionList, fos);
			fos.flush();
			fos.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
