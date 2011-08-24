package org.osmsurround.ra.report;

import java.util.Collection;
import java.util.Map;

import org.osmsurround.ra.data.Node;

public class ReportItem {

	private Map<Node, Collection<NodeDistance>> nodes;

	public ReportItem(Map<Node, Collection<NodeDistance>> nodes) {
		this.nodes = nodes;
	}

	public Map<Node, Collection<NodeDistance>> getNodes() {
		return nodes;
	}

}
