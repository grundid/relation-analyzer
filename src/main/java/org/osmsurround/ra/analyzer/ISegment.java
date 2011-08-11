package org.osmsurround.ra.analyzer;

import java.util.Collection;
import java.util.List;

import org.osmsurround.ra.data.Node;
import org.osmsurround.ra.data.Way;

public interface ISegment {

	Collection<Node> getFirstNode();

	Collection<Node> getLastNode();

	void reverse();

	boolean isReversible();

	List<ISegment> getSegments();

	List<Way> getWays();

	List<Node> getNodesFromTo(Node firstNode, Node lastNode);
}
