package org.osmsurround.ra;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.osmsurround.ra.analyzer.AggregatedSegment;
import org.osmsurround.ra.analyzer.AggregationService;
import org.osmsurround.ra.analyzer.RelationMemberService;
import org.osmsurround.ra.context.AnalyzerContext;
import org.osmsurround.ra.context.AnalyzerContextService;
import org.osmsurround.ra.data.Node;
import org.osmsurround.ra.export.GpxExport;
import org.osmsurround.ra.export.Section;
import org.osmsurround.ra.export.SectionContainer;
import org.osmsurround.ra.export.TraverseService;
import org.osmsurround.ra.segment.ISegment;
import org.osmsurround.ra.web.IntersectionNode;
import org.osmsurround.ra.web.IntersectionWebService;
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
	private IntersectionWebService intersectionWebService;

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
		intersectionWebService.initIntersectionWeb(analyzerContext);
		return analyzerContext;
	}

	public static List<Section> convert(List<ISegment> segments) {
		List<Section> sectionList = new ArrayList<Section>();

		appendSegments(sectionList, 0, segments);

		return sectionList;

	}

	public static List<Section> convertAggregated(List<AggregatedSegment> aggregatedSegments) {
		List<Section> sectionList = new ArrayList<Section>();

		int y = 0;

		for (AggregatedSegment aggregatedSegment : aggregatedSegments) {
			List<ISegment> segments = aggregatedSegment.getSegments();
			appendSegments(sectionList, y, segments);
			y++;
		}
		return sectionList;

	}

	private static void appendSegments(List<Section> sectionList, int y, List<ISegment> segments) {
		for (int x = 0; x < segments.size(); x++) {
			ISegment segment = segments.get(x);
			sectionList.add(new SectionContainer("Segment " + y + " | " + x, segment.getNodesBetween(
					segment.getStartNodes(), segment.getEndNodes())));
		}
	}

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
