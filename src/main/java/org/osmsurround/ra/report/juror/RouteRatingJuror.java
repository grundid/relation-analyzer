package org.osmsurround.ra.report.juror;

import org.osmsurround.ra.context.AnalyzerContext;
import org.osmsurround.ra.report.Rating;
import org.osmsurround.ra.report.RelationRating;
import org.springframework.stereotype.Component;

@Component
public class RouteRatingJuror extends AbstractRatingJuror {

	public RouteRatingJuror() {
		super("route");
	}

	@Override
	public RelationRating getRating(AnalyzerContext analyzerContext) {
		if (analyzerContext.getGraphs().size() == 1) {
			return new RelationRating(Rating.OK, RELATION_IN_ONE_PIECE);
		}
		else {
			return new RelationRating(Rating.DISCONNECTED, RELATION_DISCONNECTED);
		}
	}
}
