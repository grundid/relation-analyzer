package org.osmsurround.ra.report;

import java.util.Map;

import org.osmsurround.ra.context.AnalyzerContext;

public interface RatingJuror {

	boolean canRate(String type, Map<String, String> tags);

	RelationRating getRating(AnalyzerContext analyzerContext);
}
