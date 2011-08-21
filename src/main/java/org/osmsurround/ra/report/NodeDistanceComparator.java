package org.osmsurround.ra.report;

import java.util.Comparator;

public class NodeDistanceComparator implements Comparator<NodeDistance> {

	@Override
	public int compare(NodeDistance nd1, NodeDistance nd2) {

		int c = (int)(nd1.getDistance() - nd2.getDistance());

		if (c == 0) {
			c = (int)(nd1.getNode().getId() - nd2.getNode().getId());
		}

		return c;
	}

}
