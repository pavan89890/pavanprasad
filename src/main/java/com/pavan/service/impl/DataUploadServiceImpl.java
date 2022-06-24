package com.pavan.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.pavan.service.DataUploadService;
import com.pavan.service.ExpenseService;

@Service
public class DataUploadServiceImpl implements DataUploadService {

	public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

	@Autowired
	private ExpenseService expenseService;

	@Override
	public boolean hasExcelFormat(MultipartFile file) {
		if (!TYPE.equals(file.getContentType())) {
			return false;
		}
		return true;
	}

	@Override
	public Map<String, List<List<Object>>> extractExcelData(MultipartFile file) throws IOException {
		Map<String, List<List<Object>>> data = extractExcekData(file.getInputStream());
		return data;
	}

	private Map<String, List<List<Object>>> extractExcekData(InputStream inputStream) throws IOException {

		Map<String, List<List<Object>>> excelData = new LinkedHashMap<>();

		Workbook workbook = new XSSFWorkbook(inputStream);
		Iterator<Sheet> sheetsIterator = workbook.iterator();

		while (sheetsIterator.hasNext()) {
			Sheet sheet = sheetsIterator.next();
			List<List<Object>> sheetData = extractSheetData(sheet);
			excelData.put(sheet.getSheetName(), sheetData);
		}
		workbook.close();
		return excelData;
	}

	private List<List<Object>> extractSheetData(Sheet sheet) {

		List<List<Object>> sheetData = new ArrayList<>();
		List<Object> rowData = new ArrayList<>();
		Iterator<Row> rows = sheet.iterator();

		while (rows.hasNext()) {
			Row currentRow = rows.next();
			Iterator<Cell> cellsInRow = currentRow.iterator();
			rowData = new ArrayList<>();
			while (cellsInRow.hasNext()) {
				Cell currentCell = cellsInRow.next();

				switch (currentCell.getCellTypeEnum()) {

				case STRING:
					rowData.add(currentCell.getRichStringCellValue().getString());
					break;
				case NUMERIC:
					if (DateUtil.isCellDateFormatted(currentCell)) {
						rowData.add(currentCell.getDateCellValue());
					} else {
						rowData.add(currentCell.getNumericCellValue());
					}
					break;
				case BOOLEAN:
					rowData.add(currentCell.getBooleanCellValue());
					break;
				case FORMULA:
					rowData.add(currentCell.getCellFormula());
					break;
				case BLANK:
					rowData.add("");
					break;
				default:
				}
			}
			sheetData.add(rowData);
		}
		return sheetData;
	}

	@Override
	public void uploadData(Map<String, List<List<Object>>> excelData) {

		for (Map.Entry<String, List<List<Object>>> entry : excelData.entrySet()) {

			switch (entry.getKey()) {
			case "Expense":
				expenseService.bulkUpload(entry.getValue());
			case "Bank":
			case "Chit":
			case "Fd":
			case "Mutual Fund":
			case "Job":
			case "Event":
			case "User":
			case "To do":
			}

		}
	}

}
