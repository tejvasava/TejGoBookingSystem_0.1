package com.tejgo.main.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Utils {
	private static final Logger LOGGER = LoggerFactory.getLogger(Utils.class);

	private static final DateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private static final DateFormat WEB_DATE_TIME_FORMAT = new SimpleDateFormat("dd/MM/yyyy hh:mm aa");

	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");

	public static Date parseDateTime(String date) {
		try {
			if (StringUtils.isNotBlank(date))
				return DATE_TIME_FORMAT.parse(date);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}

		return null;
	}
	
	public static String formatDateTimeWeb(Date date) {
		if (Objects.nonNull(date)) {
			WEB_DATE_TIME_FORMAT.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
			return WEB_DATE_TIME_FORMAT.format(date);
		}

		return StringUtils.EMPTY;
	}
	
	public static Date generatedAtDate() {
		LocalDateTime localDateTime = LocalDateTime.now();
		Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
		return date;

	}
	
	public static String generatedDateToString(Date date) {
		String pattern = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		String dateString = dateFormat.format(date);
		return dateString;

	}
}
