package com.pavan.util;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Utility {

	public static final SimpleDateFormat yyyy_MM_dd = new SimpleDateFormat("yyyy-MM-dd");

	public static final SimpleDateFormat MM_dd = new SimpleDateFormat("MM-dd");

	public static final String[] monthNames = { "January", "February", "March", "April", "May", "June", "July",
			"August", "September", "October", "November", "December" };

	public static boolean isEmpty(Object obj) {
		try {

			if (obj instanceof Collection) {
				return obj == null || ((Collection<?>) obj).size() == 0;
			} else if (obj instanceof String) {
				return obj == null || ((String) obj).isEmpty();
			} else if (obj instanceof Integer) {
				return obj == null || Integer.valueOf(String.valueOf(obj)) == 0;
			} else if (obj instanceof Long) {
				return obj == null || Long.valueOf(String.valueOf(obj)) == 0;
			} else if (obj instanceof Float) {
				return obj == null || Float.valueOf(String.valueOf(obj)) == 0;
			} else if (obj instanceof Double) {
				return obj == null || Double.valueOf(String.valueOf(obj)) == 0;
			} else {
				return obj == null;
			}

		} catch (Exception e) {
			return false;
		}
	}

	public static String getDateDifference(LocalDate date1, LocalDate date2) {
		Period age = Period.between(date1, date2);
		String ageVal = age.getYears() + " Years, " + age.getMonths() + " Months, " + (age.getDays() + 1) + " Days";
		return ageVal;
	}

	public static String getMonthName(Integer month) {
		String monthName = "";
		if (month != null) {
			monthName = monthNames[month - 1];
		}
		return monthName;
	}

	public static void createExcelHeader(XSSFSheet sheet, String[] headers, CellStyle headerStyle) {

		Row row = sheet.createRow(0);
		int cellnum = 0;
		for (String header : headers) {
			Cell cell = row.createCell(cellnum++);
			cell.setCellValue(header);
			cell.setCellStyle(headerStyle);
		}
	}

	public static void createExcelRows(XSSFSheet sheet, List<Object[]> data) {
		int rownum = 1;
		for (Object[] objArray : data) {
			// this creates a new row in the sheet
			Row row = sheet.createRow(rownum++);
			int cellnum = 0;
			for (Object obj : objArray) {
				// this line creates a cell in the next column of that row
				Cell cell = row.createCell(cellnum++);
				if (obj instanceof String)
					cell.setCellValue((String) obj);
				else if (obj instanceof Long)
					cell.setCellValue((Long) obj);
				else if (obj instanceof Integer)
					cell.setCellValue((Integer) obj);
				else if (obj instanceof Float)
					cell.setCellValue((Float) obj);
				else if (obj instanceof Double)
					cell.setCellValue((Double) obj);
				else if (obj instanceof Date)
					cell.setCellValue((Date) obj);
			}
		}
	}

	public static CellStyle getHeaderStyle(XSSFWorkbook workbook) {
		CellStyle headerStyle = workbook.createCellStyle();
		Font font = workbook.createFont();
		font.setBold(true);
		headerStyle.setFont(font);
		return headerStyle;
	}

	public static boolean isCurrentDate(Date date1, Date date2) {
		if (date1 != null && date2 != null) {
			String dateStr1 = Utility.yyyy_MM_dd.format(date1);
			String dateStr2 = Utility.yyyy_MM_dd.format(date2);
			return dateStr1.equals(dateStr2);
		} else {
			return false;
		}
	}

	public static boolean isCurrentDateAndMonth(Date date1, Date date2) {
		if (date1 != null && date2 != null) {
			String dateStr1 = Utility.MM_dd.format(date1);
			String dateStr2 = Utility.MM_dd.format(date2);
			return dateStr1.equals(dateStr2);
		} else {
			return false;
		}
	}

}
