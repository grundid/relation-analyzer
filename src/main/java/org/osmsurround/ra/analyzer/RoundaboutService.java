package org.osmsurround.ra.analyzer;

import java.util.List;

import org.osmsurround.ra.data.Node;
import org.osmsurround.ra.data.Way;
import org.springframework.stereotype.Service;

@Service
public class RoundaboutService {

	public boolean isRoundabout(Way way) {
		List<Node> nodes = way.getNodes();
		if (nodes.size() > 1) {
			Node firstNode = nodes.get(0);
			Node lastNode = nodes.get(nodes.size() - 1);
			return firstNode.equals(lastNode);
		}
		else
			return false;
	}
}
