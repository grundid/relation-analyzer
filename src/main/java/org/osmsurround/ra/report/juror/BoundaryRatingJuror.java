package org.osmsurround.ra.report.juror;

import org.osmsurround.ra.report.Rating;
import org.osmsurround.ra.report.RelationRating;
import org.osmtools.ra.context.AnalyzerContext;
import org.osmtools.ra.graph.Graph;
import org.springframework.stereotype.Component;

@Component
public class BoundaryRatingJuror extends AbstractRatingJuror {

	public BoundaryRatingJuror() {
		super("boundary");
	}

	@Override
	public RelationRating getRating(AnalyzerContext analyzerContext) {
		if (areAllGraphsClosed(analyzerContext.getGraphs())) {
			return new RelationRating(Rating.OK, RELATION_IN_ONE_PIECE);
		}
		else {
			return new RelationRating(Rating.DISCONNECTED, RELATION_DISCONNECTED);
		}
	}

	private boolean areAllGraphsClosed(Iterable<Graph> graphs) {
		for (Graph graph : graphs) {
			if (graph.getLeaves().size() != 1)
				return false;
		}
		return true;
	}
}
