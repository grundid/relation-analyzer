package org.osmsurround.ra.dijkstra;

public interface Edge {

	Vertex getSource();

	Vertex getDestination();

	int getWeight();
}
