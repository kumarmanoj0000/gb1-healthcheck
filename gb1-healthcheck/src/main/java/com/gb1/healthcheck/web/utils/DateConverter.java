package com.gb1.healthcheck.web.utils;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.util.StrutsTypeConverter;

public class DateConverter extends StrutsTypeConverter {
	private static final Logger logger = Logger.getLogger(DateConverter.class);
	private static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";

	private String dateTimePattern;

	public DateConverter() {
		this(DEFAULT_DATE_PATTERN);
	}

	public DateConverter(String pattern) {
		this.dateTimePattern = pattern;
	}

	public void setDateTimePattern(String pattern) {
		Validate.notNull(pattern);
		this.dateTimePattern = pattern;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Date convertFromString(Map context, String[] values, Class toClass) {
		Date date;

		if (values != null && values.length > 0 && StringUtils.isNotBlank(values[0])) {
			try {
				date = DateUtils.parseDate(values[0], new String[] { dateTimePattern });
			}
			catch (ParseException e) {
				logger.error("Error converting value [" + values[0] + "] to java.util.Date", e);
				date = null;
			}
		}
		else {
			date = null;
		}

		return date;
	}

	@Override
	@SuppressWarnings("unchecked")
	public String convertToString(Map context, Object data) {
		String text;

		if (data instanceof Date) {
			text = DateFormatUtils.format((Date) data, dateTimePattern);
		}
		else {
			text = "";
		}

		return text;
	}
}
