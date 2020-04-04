package com.pavan.util;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;

public class DateUtil {

	public static final SimpleDateFormat yyyy_MM_dd = new SimpleDateFormat("yyyy-MM-dd");
	public static final SimpleDateFormat fileCreationDateFormat = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss");
	public static final SimpleDateFormat MM_dd = new SimpleDateFormat("MM-dd");
	public static final String[] MONTH_NAMES = { "January", "February", "March", "April", "May", "June", "July",
			"August", "September", "October", "November", "December" };

	public static String getDateDifference(LocalDate date1, LocalDate date2) {
		Period age = Period.between(date1, date2);
		String ageVal = age.getYears() + " Years, " + age.getMonths() + " Months, " + (age.getDays() + 1) + " Days";
		return ageVal;
	}

	public static String getMonthName(Integer month) {
		String monthName = "";
		if (month != null) {
			monthName = MONTH_NAMES[month - 1];
		}
		return monthName;
	}

	public static boolean isCurrentDate(Date date1, Date date2) {
		if (date1 != null && date2 != null) {
			String dateStr1 = yyyy_MM_dd.format(date1);
			String dateStr2 = yyyy_MM_dd.format(date2);
			return dateStr1.equals(dateStr2);
		} else {
			return false;
		}
	}

	public static boolean isCurrentDateAndMonth(Date date1, Date date2) {
		if (date1 != null && date2 != null) {
			String dateStr1 = MM_dd.format(date1);
			String dateStr2 = MM_dd.format(date2);
			return dateStr1.equals(dateStr2);
		} else {
			return false;
		}
	}

}
