package org.osmsurround.ra.analyzer;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.osmtools.ra.data.Member;

public abstract class RouteMemberFilter {

	private static final Set<String> IGNORED_ROLES = new HashSet<String>(Arrays.asList("stop", "stop_entry_only",
			"stop_exit_only", "platform", "platform_entry_only", "platform_exit_only"));

	public static boolean isRouteMember(Member member) {
		return !IGNORED_ROLES.contains(member.getRole());
	}
}
