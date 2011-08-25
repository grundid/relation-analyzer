package org.osmsurround.ra.search;

import java.util.List;

import org.osmsurround.ra.dao.Relation;

public class SearchResult {

	private List<Relation> relations;
	private int totalAmount;
	private int displayAmount;

	public SearchResult(List<Relation> relations) {
		this.totalAmount = relations.size();
		this.displayAmount = Math.min(100, totalAmount);
		this.relations = relations.subList(0, displayAmount);
	}

	public List<Relation> getRelations() {
		return relations;
	}

	public int getTotalAmount() {
		return totalAmount;
	}

	public int getDisplayAmount() {
		return displayAmount;
	}
}
