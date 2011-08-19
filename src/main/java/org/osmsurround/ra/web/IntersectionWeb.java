package org.osmsurround.ra.web;

import java.util.Set;

public class IntersectionWeb {

	private Set<IntersectionNode> leaves;

	public IntersectionWeb(Set<IntersectionNode> leaves) {
		this.leaves = leaves;
	}

	public Set<IntersectionNode> getLeaves() {
		return leaves;
	}
}
