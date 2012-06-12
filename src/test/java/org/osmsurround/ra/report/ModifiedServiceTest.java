package org.osmsurround.ra.report;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Test;
import org.osmsurround.ra.TestBase;
import org.springframework.beans.factory.annotation.Autowired;

public class ModifiedServiceTest extends TestBase {

	@Autowired
	private ModifiedService modifiedService;

	private long createDate(int year, int month, int day, int hour, int min, int sec) {
		Calendar c = Calendar.getInstance();
		c.set(year, month, day, hour, min, sec);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTimeInMillis();
	}

	@Test
	public void testGetModified() throws Exception {
		long now = createDate(2012, 5, 2, 20, 15, 0);

		assertModified("modified.less.minute", 0,
				modifiedService.calculateModified(createDate(2012, 5, 2, 20, 14, 30), now));
		assertModified("modified.minutes", 4,
				modifiedService.calculateModified(createDate(2012, 5, 2, 20, 10, 30), now));
		assertModified("modified.hour", 1, modifiedService.calculateModified(createDate(2012, 5, 2, 19, 15, 00), now));
		assertModified("modified.hours", 5, modifiedService.calculateModified(createDate(2012, 5, 2, 14, 20, 00), now));
		assertModified("modified.day", 1, modifiedService.calculateModified(createDate(2012, 5, 1, 18, 15, 00), now));
		assertModified("modified.days", 10, modifiedService.calculateModified(createDate(2012, 4, 23, 18, 15, 00), now));
		assertModified("modified.months", 2, modifiedService.calculateModified(createDate(2012, 3, 2, 18, 15, 00), now));
		assertModified("modified.year", 1, modifiedService.calculateModified(createDate(2011, 3, 2, 18, 15, 00), now));
		assertModified("modified.years", 2, modifiedService.calculateModified(createDate(2010, 3, 2, 18, 15, 00), now));
		assertModified("modified.unknown", 0,
				modifiedService.calculateModified(createDate(2013, 3, 2, 18, 15, 00), now));

	}

	private static void assertModified(String expectedMessageCode, int expectedAmount, ModifiedModel model) {
		assertEquals(expectedMessageCode, model.getMessageCode());
		assertEquals(expectedAmount, model.getAmount());
	}

}
