package org.osmsurround.ra.report;

import java.util.ArrayList;
import java.util.List;

public class AnalyzerReport {

	private List<AnalyzerReportItem> reportItems = new ArrayList<AnalyzerReportItem>();

	public void addReportItem(AnalyzerReportItem reportItem) {
		reportItems.add(reportItem);
	}

	public List<AnalyzerReportItem> getReportItems() {
		return reportItems;
	}
}
