package org.osmsurround.ra.data;

import java.io.Serializable;
import java.util.List;

public class Way implements Serializable {

	private long id;
	private List<Node> nodes;

	public Way(long id, List<Node> nodes) {
		this.id = id;
		this.nodes = nodes;
	}

	public long getId() {
		return id;
	}

	public List<Node> getNodes() {
		return nodes;
	}

}
