package org.osmsurround.tags.builder;

import java.text.DecimalFormat;

import org.osmtools.ra.data.Node;

public class PotlatchLinkBuilder extends LinkBuilder {

	@Override
	public String buildLinkForNodes(Node node1, Node node2) {
		DecimalFormat df = createFormatter();
		return "http://www.openstreetmap.org/edit?lat=" + df.format(node2.getLat()) + "&lon="
				+ df.format(node2.getLon()) + "&zoom=18&node=" + node2.getId();
	}
}
