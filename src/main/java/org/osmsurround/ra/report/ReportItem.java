package org.osmsurround.ra.report;

import java.util.Collection;

public class ReportItem {

	private Collection<EndNodeDistances> endNodeDistances;

	public ReportItem(Collection<EndNodeDistances> endNodeDistances) {
		this.endNodeDistances = endNodeDistances;
	}

	public Collection<EndNodeDistances> getEndNodeDistances() {
		return endNodeDistances;
	}

}
