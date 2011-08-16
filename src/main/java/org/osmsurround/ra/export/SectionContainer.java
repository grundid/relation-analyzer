package org.osmsurround.ra.export;

public class SectionContainer implements Section {

	private String name;
	private Iterable<? extends LonLat> coordinates;

	public SectionContainer(String name, Iterable<? extends LonLat> coordinates) {
		this.name = name;
		this.coordinates = coordinates;
	}

	@Override
	public String getName() {
		return name;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterable<LonLat> getCoordinates() {
		return (Iterable<LonLat>)coordinates;
	}

}
