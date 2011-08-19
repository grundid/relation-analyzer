package org.osmsurround.ra.context;

import java.util.List;

import org.osmsurround.ra.AnalyzerException;
import org.osmsurround.ra.analyzer.AggregatedSegment;
import org.osmsurround.ra.data.Relation;
import org.osmsurround.ra.segment.ISegment;
import org.osmsurround.ra.web.IntersectionWeb;

public class AnalyzerContext {

	private Relation relation;

	private List<ISegment> segments;
	private List<AggregatedSegment> aggregatedSegments;
	private List<IntersectionWeb> intersectionWebs;

	AnalyzerContext(Relation relation) {
		this.relation = relation;
	}

	public Relation getRelation() {
		return relation;
	}

	public List<ISegment> getSegments() {
		if (segments == null)
			throw new AnalyzerException("Segments not initialized");
		return segments;
	}

	public void setSegments(List<ISegment> segments) {
		this.segments = segments;
	}

	public List<AggregatedSegment> getAggregatedSegments() {
		if (aggregatedSegments == null)
			throw new AnalyzerException("Aggregated segments not initialized");
		return aggregatedSegments;
	}

	public void setAggregatedSegments(List<AggregatedSegment> aggregatedSegments) {
		this.aggregatedSegments = aggregatedSegments;
	}

	public List<IntersectionWeb> getIntersectionWebs() {
		if (intersectionWebs == null)
			throw new AnalyzerException("Intersection web not initialized");
		return intersectionWebs;
	}

	public void setIntersectionWebs(List<IntersectionWeb> intersectionWebs) {
		this.intersectionWebs = intersectionWebs;
	}

}
