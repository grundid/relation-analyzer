package org.osmsurround.ra;

public class AnalyzeRelationModel {

	private Long relationId;
	private boolean noCache;

	public Long getRelationId() {
		return relationId;
	}

	public void setRelationId(Long relationId) {
		this.relationId = relationId;
	}

	public boolean isNoCache() {
		return noCache;
	}

	public void setNoCache(boolean noCache) {
		this.noCache = noCache;
	}
}
