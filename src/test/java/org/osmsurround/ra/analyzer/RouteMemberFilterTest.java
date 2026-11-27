package org.osmsurround.ra.analyzer;

import static org.junit.Assert.*;

import org.junit.Test;
import org.osmtools.ra.data.Member;

public class RouteMemberFilterTest {

	@Test
	public void testIgnoredRoles() throws Exception {
		assertFalse(RouteMemberFilter.isRouteMember(new Member(null, "stop")));
		assertFalse(RouteMemberFilter.isRouteMember(new Member(null, "stop_entry_only")));
		assertFalse(RouteMemberFilter.isRouteMember(new Member(null, "stop_exit_only")));
		assertFalse(RouteMemberFilter.isRouteMember(new Member(null, "platform")));
		assertFalse(RouteMemberFilter.isRouteMember(new Member(null, "platform_entry_only")));
		assertFalse(RouteMemberFilter.isRouteMember(new Member(null, "platform_exit_only")));
	}

	@Test
	public void testKeptRoles() throws Exception {
		assertTrue(RouteMemberFilter.isRouteMember(new Member(null, "")));
		assertTrue(RouteMemberFilter.isRouteMember(new Member(null, "forward")));
		assertTrue(RouteMemberFilter.isRouteMember(new Member(null, "backward")));
		assertTrue(RouteMemberFilter.isRouteMember(new Member(null, "alternative")));
	}
}
