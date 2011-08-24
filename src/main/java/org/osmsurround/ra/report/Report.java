package org.osmsurround.ra.report;

import java.util.List;

public class Report {

	private RelationRating relationRating;
	private RelationInfo relationInfo;
	private List<ReportItem> reportItems;

	public RelationRating getRelationRating() {
		return relationRating;
	}

	public void setRelationRating(RelationRating relationRating) {
		this.relationRating = relationRating;
	}

	public RelationInfo getRelationInfo() {
		return relationInfo;
	}

	public void setRelationInfo(RelationInfo relationInfo) {
		this.relationInfo = relationInfo;
	}

	public List<ReportItem> getReportItems() {
		return reportItems;
	}

	public void setReportItems(List<ReportItem> reportItems) {
		this.reportItems = reportItems;
	}

}
