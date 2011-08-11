package org.osmsurround.ra.data;

import java.io.Serializable;

public class Member implements Serializable {

	private Way way;
	private String role;

	public Member(Way way, String role) {
		this.way = way;
		this.role = role;
	}

	public Way getWay() {
		return way;
	}

	public String getRole() {
		return role;
	}

}
