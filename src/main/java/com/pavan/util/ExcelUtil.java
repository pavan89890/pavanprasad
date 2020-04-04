package com.pavan.util;

import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil {

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

	public static void createExcelHeader(XSSFSheet sheet, String[] headers, CellStyle headerStyle) {
	
		Row row = sheet.createRow(0);
		int cellnum = 0;
		for (String header : headers) {
			Cell cell = row.createCell(cellnum++);
			cell.setCellValue(header);
			cell.setCellStyle(headerStyle);
		}
	}

	public static CellStyle getHeaderStyle(XSSFWorkbook workbook) {
		CellStyle headerStyle = workbook.createCellStyle();
		Font font = workbook.createFont();
		font.setBold(true);
		headerStyle.setFont(font);
		return headerStyle;
	}

}
