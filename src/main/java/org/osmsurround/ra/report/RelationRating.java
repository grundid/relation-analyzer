package org.osmsurround.ra.report;

public class RelationRating {

	private Rating rating;
	private String messageCode;

	public RelationRating(Rating rating, String messageCode) {
		this.rating = rating;
		this.messageCode = messageCode;
	}

	public Rating getRating() {
		return rating;
	}

	public String getMessageCode() {
		return messageCode;
	}

}
