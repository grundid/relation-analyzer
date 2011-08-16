package org.osmsurround.ra.export;


public interface Section {

	String getName();

	Iterable<LonLat> getCoordinates();
}
