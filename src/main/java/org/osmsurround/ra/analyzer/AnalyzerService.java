package org.osmsurround.ra.analyzer;

import java.util.ArrayList;
import java.util.List;

import org.osmtools.ra.analyzer.AggregationService;
import org.osmtools.ra.context.AnalyzerContext;
import org.osmtools.ra.context.AnalyzerContextService;
import org.osmtools.ra.data.Member;
import org.osmtools.ra.graph.GraphService;
import org.osmtools.ra.segment.ConnectableSegment;
import org.osmtools.ra.segment.SegmentFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnalyzerService {

	@Autowired
	private SegmentFactory segmentFactory;
	@Autowired
	private AnalyzerContextService analyzerContextService;
	@Autowired
	private AggregationService aggregationService;
	@Autowired
	private GraphService graphService;

	public AnalyzerContext analyzeRelation(long relationId, boolean noCache) {
		AnalyzerContext analyzerContext = analyzerContextService.createAnalyzerContext(relationId, noCache);
		analyzeContext(analyzerContext);
		return analyzerContext;
	}

	public void analyzeContext(AnalyzerContext analyzerContext) {
		initSegments(analyzerContext);
		aggregationService.aggregate(analyzerContext);
		graphService.initGraph(analyzerContext);
	}

	public void initSegments(AnalyzerContext analyzerContext) {
		List<ConnectableSegment> segments = new ArrayList<ConnectableSegment>();
		for (Member member : analyzerContext.getRelation().getMembers()) {
			if (RouteMemberFilter.isRouteMember(member))
				segments.add(segmentFactory.createSegment(member));
		}
		analyzerContext.setSegments(segments);
	}
}
