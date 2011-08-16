package org.osmsurround.ra;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.osmsurround.ra.analyzer.AggregatedSegment;
import org.osmsurround.ra.analyzer.AggregationService;
import org.osmsurround.ra.analyzer.IntersectionNode;
import org.osmsurround.ra.analyzer.IntersectionNodeWebCreator;
import org.osmsurround.ra.analyzer.RoleService;
import org.osmsurround.ra.data.Node;
import org.osmsurround.ra.data.Relation;
import org.osmsurround.ra.export.GpxExport;
import org.osmsurround.ra.export.Section;
import org.osmsurround.ra.export.SectionContainer;
import org.osmsurround.ra.export.TraverseService;
import org.osmsurround.ra.segment.ISegment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class HelperService {

	@Autowired
	private RoleService roleService;
	@Autowired
	private RelationLoaderService relationLoaderService;
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private AggregationService aggregationService;
	@Autowired
	private TraverseService traverseService;
	@Autowired
	private GpxExport gpxExport;

	public Map<String, List<ISegment>> loadSplittedRelation(long relationId) {
		TestUtils.prepareRestTemplateForRelation(restTemplate, relationId);
		Relation osmRelation = relationLoaderService.loadRelation(relationId);
		return roleService.splitRelationByRole(osmRelation);
	}

	public Map<String, List<AggregatedSegment>> loadSplittedAndAggregatedRelation(long relationId) {
		Map<String, List<AggregatedSegment>> aggregatedRelation = new HashMap<String, List<AggregatedSegment>>();
		Map<String, List<ISegment>> splittedRelation = loadSplittedRelation(relationId);
		for (Entry<String, List<ISegment>> entry : splittedRelation.entrySet()) {
			List<AggregatedSegment> list = aggregationService.aggregate(entry.getValue());
			aggregatedRelation.put(entry.getKey(), list);
		}
		return aggregatedRelation;
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

	public void exportGpx(IntersectionNode intersectionNode, IntersectionNodeWebCreator webCreator) {
		List<Node> traverse = traverseService.traverse(intersectionNode, webCreator.getNodesAmount());

		List<Section> sectionList = new ArrayList<Section>();
		sectionList.add(new SectionContainer("test", traverse));

		exportSimple(sectionList);

	}

	public void exportSimple(List<Section> sectionList) {
		try {
			FileOutputStream fos = new FileOutputStream("c:\\test.gpx");

			gpxExport.export(sectionList, fos);
			fos.flush();
			fos.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
