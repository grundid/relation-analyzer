package org.osmsurround.ra.stats;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.osmsurround.ra.data.Way;

public class HighwayHierarchy {

	private Collection<HighwayHierarchy> subHierarchies = new ArrayList<HighwayHierarchy>();

	private String name;
	private double length;
	private int wayCount;
	private Map<String, String> pattern = new HashMap<String, String>();

	public HighwayHierarchy(String name, String... patterns) {
		this.name = name;
		for (int x = 0; x < patterns.length; x += 2) {
			pattern.put(patterns[x], patterns[x + 1]);
		}
	}

	public void addSubHierarchy(HighwayHierarchy hierarchy) {
		subHierarchies.add(hierarchy);
	}

	public boolean addWay(Way way, double length) {
		boolean added = false;

		if (containsAll(way.getTags())) {
			added = true;
			this.length += length;
			wayCount++;
		}

		if (!added) {
			for (HighwayHierarchy hierarchy : subHierarchies) {
				added = hierarchy.addWay(way, length);
				if (added) {
					this.length += length;
					break;
				}
			}
		}
		return added;
	}

	private boolean containsAll(Map<String, String> target) {
		if (pattern.isEmpty())
			return false;
		for (Entry<String, String> entry : pattern.entrySet()) {
			String value = target.get(entry.getKey());
			if (value == null)
				return false;

			if (!"*".equals(entry.getValue()) && !value.equals(entry.getValue()))
				return false;
		}
		return true;
	}

	private Distribution createDistribution() {
		return new Distribution(name, length, wayCount);
	}

	public void appendDistributions(List<Distribution> distributions, int targetLayer) {
		appendDistributions(distributions, targetLayer, 0);
	}

	private void appendDistributions(List<Distribution> distributions, int targetLayer, int currentLayer) {
		if (targetLayer == currentLayer) {
			for (HighwayHierarchy hierarchy : subHierarchies) {
				distributions.add(hierarchy.createDistribution());
			}
		}
		else {
			for (HighwayHierarchy hierarchy : subHierarchies) {
				hierarchy.appendDistributions(distributions, targetLayer, currentLayer + 1);
			}
		}
	}

	public String getName() {
		return name;
	}

	public double getLength() {
		return length;
	}

}
