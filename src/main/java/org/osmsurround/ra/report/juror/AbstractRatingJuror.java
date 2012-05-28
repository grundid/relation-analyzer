package org.osmsurround.ra.report.juror;

import java.util.Map;

import org.osmsurround.ra.report.RatingJuror;

public abstract class AbstractRatingJuror implements RatingJuror {

	protected static final String RELATION_IN_ONE_PIECE = "relation.in.one.piece";
	protected static final String RELATION_DISCONNECTED = "relation.disconnect";

	private String responsibleTagType;

	protected AbstractRatingJuror(String responsibleTagType) {
		this.responsibleTagType = responsibleTagType;
	}

	@Override
	public boolean canRate(String type, Map<String, String> tags) {
		return responsibleTagType.equals(type);
	}
}
