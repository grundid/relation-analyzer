package org.osmsurround.ra.stats;

import java.util.HashSet;
import java.util.Set;

public class Category {

	private String name;
	private Set<String> values = new HashSet<String>();

	public Category(String name, String... values) {
		this.name = name;
		for (String value : values)
			this.values.add(value);
	}

	public boolean isCategory(String value) {
		//		if (values.isEmpty())
		//			System.out.println("Unknown: " + value);
		return values.isEmpty() || values.contains(value);
	}

	public String getName() {
		return name;
	}

}
