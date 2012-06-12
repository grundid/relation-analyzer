package org.osmsurround.ra.report;

import org.springframework.stereotype.Service;

@Service
public class ModifiedService {

	private static final long MIN = 60 * 1000;
	private static final long HOUR = 60 * MIN;
	private static final long DAY = 24 * HOUR;
	private static final long MONTH = 29 * DAY;
	private static final long YEAR = 365 * DAY;

	// < 1 min, 1-60 min, 1-24 stunden, 1-30 tage, 1-12 monate, > 1 jahr

	private static final ModifiedRange[] ranges;

	static {
		ranges = new ModifiedRange[] { new ModifiedRange(0, MIN, MIN, "modified.less.minute"),
				new ModifiedRange(MIN, 2 * MIN, MIN, "modified.minute"),
				new ModifiedRange(2 * MIN, 60 * MIN, MIN, "modified.minutes"),
				new ModifiedRange(HOUR, 2 * HOUR, HOUR, "modified.hour"),
				new ModifiedRange(2 * HOUR, 24 * HOUR, HOUR, "modified.hours"),
				new ModifiedRange(DAY, 2 * DAY, DAY, "modified.day"),
				new ModifiedRange(2 * DAY, 29 * DAY, DAY, "modified.days"),
				new ModifiedRange(MONTH, 2 * MONTH, MONTH, "modified.month"),
				new ModifiedRange(2 * MONTH, 12 * MONTH, MONTH, "modified.months"),
				new ModifiedRange(YEAR, 2 * YEAR, YEAR, "modified.year"),
				new ModifiedRange(2 * YEAR, Long.MAX_VALUE, YEAR, "modified.years") };
	}

	public ModifiedModel calculateModified(long modified) {
		return calculateModified(modified, System.currentTimeMillis());
	}

	public ModifiedModel calculateModified(long modified, long now) {

		long diff = now - modified;

		for (ModifiedRange range : ranges) {
			if (diff >= range.getFrom() && diff < range.getTo()) {
				int amount = (int)Math.floor(diff / range.getFactor());
				return new ModifiedModel(amount, range.getMessageCode());
			}
		}

		return new ModifiedModel(0, "modified.unknown");
	}
}
