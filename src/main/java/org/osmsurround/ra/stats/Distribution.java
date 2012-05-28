package org.osmsurround.ra.stats;

public class Distribution {

	private String name;
	private int wayCount;
	private double length;
	private double percent;

	public Distribution(String name) {
		this.name = name;
	}

	public Distribution(String name, double length, int wayCount) {
		this.name = name;
		this.length = length;
		this.wayCount = wayCount;
	}

	public void addWayLength(double length) {
		wayCount++;
		this.length += length;
	}

	public String getName() {
		return name;
	}

	public int getWayCount() {
		return wayCount;
	}

	public double getLength() {
		return length;
	}

	public double getPercent() {
		return percent;
	}

	public void finish(double total) {
		percent = length / total;
		if (Double.isNaN(percent))
			percent = 0;
	}
}
