package org.osmsurround.ra.stats;

import java.util.Collection;

public class RelationStatistics {

	private double length;

	private Collection<Distribution> distributions;

	public RelationStatistics(Collection<Distribution> distributions) {
		this.distributions = distributions;
		for (Distribution distribution : distributions)
			length += distribution.getLength();
	}

	public void addWay(String category, double length) {
		this.length += length;
		for (Distribution distribution : distributions) {
			if (category.equals(distribution.getName()))
				distribution.addWayLength(length);
		}
	}

	public double getLength() {
		return length;
	}

	public Collection<Distribution> getDistributions() {
		return distributions;
	}

	public void finish() {
		for (Distribution distribution : distributions) {
			distribution.finish(length);
		}
	}

}
