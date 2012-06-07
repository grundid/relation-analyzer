package org.osmsurround.ra.elevation;

public class Elevation {

	private double distance;
	private int height;

	public Elevation(double distance, int height) {
		this.distance = distance;
		this.height = height;
	}

	public double getDistance() {
		return distance;
	}

	public int getHeight() {
		return height;
	}
}
