package org.osmsurround.ra.report;

import java.util.ArrayList;
import java.util.List;

public class AnalyzerReportItem {

	private String role;
	private List<Object> relationItems = new ArrayList<Object>();

	public AnalyzerReportItem(String role) {
		this.role = role;
	}

	public String getRole() {
		return role;
	}

	public void addRelationItem(Object object) {
		relationItems.add(object);
	}

	public List<Object> getRelationItems() {
		return relationItems;
	}

}
