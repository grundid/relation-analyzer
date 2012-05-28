/**
 * This file is part of Relation Analyzer for OSM.
 * Copyright (c) 2001 by Adrian Stabiszewski, as@grundid.de
 *
 * Relation Analyzer is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Relation Analyzer is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with Relation Analyzer.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.osmsurround.ra.data;

import java.io.Serializable;

import javax.annotation.Generated;

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
	@Generated("eclipse")
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int)(id ^ (id >>> 32));
		result = prime * result + Float.floatToIntBits(lat);
		result = prime * result + Float.floatToIntBits(lon);
		return result;
	}

	@Override
	@Generated("eclipse")
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
