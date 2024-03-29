package com.gb1.healthcheck.web.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;

import com.gb1.healthcheck.web.meals.MealInstantConverter;

public class DateConverterTest {
	@Test
	public void testConvertFromString() throws ParseException {
		String text = "2007-10-18 11:00";
		Date expectedInstant = parseInstant(text);

		DateConverter c = new MealInstantConverter();
		Date date = c.convertFromString(null, new String[] { text }, null);

		assertEquals(expectedInstant, date);
	}

	@Test
	public void testConvertFromStringInvalidInputs() {
		DateConverter c = new MealInstantConverter();

		assertNull(c.convertFromString(null, null, null));
		assertNull(c.convertFromString(null, new String[] {}, null));
		assertNull(c.convertFromString(null, new String[] { "" }, null));
	}

	@Test
	public void testConvertToString() throws ParseException {
		String expectedText = "2007-10-18 11:00";
		Date date = parseInstant(expectedText);

		DateConverter c = new MealInstantConverter();
		String text = c.convertToString(null, date);

		assertEquals(expectedText, text);
	}

	@Test
	public void testConvertToStringInvalidInputs() {
		DateConverter c = new MealInstantConverter();

		assertEquals("", c.convertToString(null, null));
		assertEquals("", c.convertToString(null, "Not a date"));
	}

	private Date parseInstant(String text) throws ParseException {
		return DateUtils.parseDate(text, new String[] { "yyyy-MM-dd HH:mm" });
	}
}
