package com.gb1.healthcheck.web.utils;

import java.text.ParseException;
import java.util.Date;

import junit.framework.TestCase;

import org.apache.commons.lang.time.DateUtils;

import com.gb1.healthcheck.web.meals.MealInstantConverter;

public class DateConverterTest extends TestCase {
	public void testConvertFromString() throws ParseException {
		String text = "2007-10-18 11:00:00";
		Date expectedInstant = parseInstant(text);

		DateConverter c = new DateConverter(MealInstantConverter.DEFAULT_DATE_TIME_FORMAT);
		Date date = c.convertFromString(null, new String[] { text }, null);

		assertEquals(expectedInstant, date);
	}

	public void testConvertFromStringInvalidInputs() {
		DateConverter c = new DateConverter(MealInstantConverter.DEFAULT_DATE_TIME_FORMAT);

		assertNull(c.convertFromString(null, null, null));
		assertNull(c.convertFromString(null, new String[] {}, null));
		assertNull(c.convertFromString(null, new String[] { "" }, null));
	}

	public void testConvertToString() throws ParseException {
		String expectedText = "2007-10-18 11:00:00";
		Date date = parseInstant(expectedText);

		DateConverter c = new DateConverter(MealInstantConverter.DEFAULT_DATE_TIME_FORMAT);
		String text = c.convertToString(null, date);

		assertEquals(expectedText, text);
	}

	public void testConvertToStringInvalidInputs() {
		DateConverter c = new DateConverter(MealInstantConverter.DEFAULT_DATE_TIME_FORMAT);

		assertEquals("", c.convertToString(null, null));
		assertEquals("", c.convertToString(null, "Not a date"));
	}

	private Date parseInstant(String text) throws ParseException {
		return DateUtils.parseDate(text,
				new String[] { MealInstantConverter.DEFAULT_DATE_TIME_FORMAT });
	}
}
