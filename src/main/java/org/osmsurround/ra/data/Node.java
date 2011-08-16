package org.osmsurround.ra.data;

import java.io.Serializable;

import org.osmsurround.ra.export.LonLat;

public class Node implements Serializable, LonLat {

	private long id;
	private float lat;
	private float lon;

	public Node(long id, float lat, float lon) {
		this.id = id;
		this.lat = lat;
		this.lon = lon;
	}

	public long getId() {
		return id;
	}

	@Override
	public float getLat() {
		return lat;
	}

	@Override
	public float getLon() {
		return lon;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int)(id ^ (id >>> 32));
		result = prime * result + Float.floatToIntBits(lat);
		result = prime * result + Float.floatToIntBits(lon);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Node other = (Node)obj;
		if (id != other.id)
			return false;
		if (Float.floatToIntBits(lat) != Float.floatToIntBits(other.lat))
			return false;
		if (Float.floatToIntBits(lon) != Float.floatToIntBits(other.lon))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "NodeId: " + id;
	}

}
