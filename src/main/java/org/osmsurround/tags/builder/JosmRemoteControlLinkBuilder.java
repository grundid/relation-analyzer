package org.osmsurround.tags.builder;

import java.text.DecimalFormat;

import org.osmsurround.ra.data.Node;

public class JosmRemoteControlLinkBuilder extends LinkBuilder {

	private String right, left, top, bottom;
	private static final float BORDER_SPACE_TO_ADD = 0.005f;

	@Override
	public String buildLinkForNodes(Node node1, Node node2) {
		initArea(node1, node2);
		return "http://localhost:8111/load_and_zoom?left=" + left + "&right=" + right + "&top=" + top + "&bottom="
				+ bottom + "&select=node" + node1.getId() + ",node" + node2.getId();

	}

	private void initArea(Node prevNode, Node nextNode) {

		DecimalFormat df = createFormatter();

		if (prevNode.getLat() > nextNode.getLat()) {
			top = df.format(prevNode.getLat() + BORDER_SPACE_TO_ADD);
			bottom = df.format(nextNode.getLat() - BORDER_SPACE_TO_ADD);
		}
		else {
			top = df.format(nextNode.getLat() + BORDER_SPACE_TO_ADD);
			bottom = df.format(prevNode.getLat() - BORDER_SPACE_TO_ADD);
		}

		if (prevNode.getLon() > nextNode.getLon()) {
			right = df.format(prevNode.getLon() + BORDER_SPACE_TO_ADD);
			left = df.format(nextNode.getLon() - BORDER_SPACE_TO_ADD);
		}
		else {
			right = df.format(nextNode.getLon() + BORDER_SPACE_TO_ADD);
			left = df.format(prevNode.getLon() - BORDER_SPACE_TO_ADD);
		}
	}
}
