package org.osmsurround.ra.web;

import java.util.ArrayList;
import java.util.List;

import org.osmsurround.ra.analyzer.AggregatedSegment;
import org.osmsurround.ra.context.AnalyzerContext;
import org.springframework.stereotype.Service;

@Service
public class IntersectionWebService {

	public void initIntersectionWeb(AnalyzerContext analyzerContext) {
		List<AggregatedSegment> aggregatedSegments = analyzerContext.getAggregatedSegments();
		List<IntersectionWeb> webList = createIntersectionWebs(aggregatedSegments);
		analyzerContext.setIntersectionWebs(webList);
	}

	private List<IntersectionWeb> createIntersectionWebs(List<AggregatedSegment> aggregatedSegments) {
		List<IntersectionWeb> webList = new ArrayList<IntersectionWeb>();
		for (AggregatedSegment aggregatedSegment : aggregatedSegments) {
			IntersectionNodeWebCreator intersectionNodeWebCreator = new IntersectionNodeWebCreator(
					aggregatedSegment.getSegments());
			webList.add(intersectionNodeWebCreator.createWeb());
		}
		return webList;
	}
}
