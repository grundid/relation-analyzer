package org.osmsurround.ra.report;

import java.util.List;

public class Report {

	private Rating rating = Rating.UNKNOWN;
	private String messageCode;

	private List<ReportItem> reportItems;

	public void setRating(Rating rating) {
		this.rating = rating;
	}

	public void setMessageCode(String messageCode) {
		this.messageCode = messageCode;
	}

	public void setReportItems(List<ReportItem> reportItems) {
		this.reportItems = reportItems;
	}

	public Rating getRating() {
		return rating;
	}

	public String getMessageCode() {
		return messageCode;
	}

	public List<ReportItem> getReportItems() {
		return reportItems;
	}
}
