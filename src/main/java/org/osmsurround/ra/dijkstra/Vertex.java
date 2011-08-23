package org.osmsurround.ra.dijkstra;

import org.osmsurround.ra.data.Node;

public interface Vertex {

	Node getNode();

	@Override
	int hashCode();

	@Override
	boolean equals(Object obj);
}
