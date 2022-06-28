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

import com.pavan.service.BankService;
import com.pavan.service.ChitService;
import com.pavan.service.DataUploadService;
import com.pavan.service.EventService;
import com.pavan.service.ExpenseService;
import com.pavan.service.FdService;
import com.pavan.service.JobService;
import com.pavan.service.MutualFundService;
import com.pavan.service.TodoService;

@Service
public class DataUploadServiceImpl implements DataUploadService {

	public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

	@Autowired
	private ExpenseService expenseService;

	@Autowired
	private BankService bankService;

	@Autowired
	private ChitService chitService;

	@Autowired
	private FdService fdService;

	@Autowired
	private MutualFundService mfService;

	@Autowired
	private JobService jobService;

	@Autowired
	private EventService eventService;

	@Autowired
	private TodoService todoService;

	@Override
	public boolean hasExcelFormat(MultipartFile file) {
		if (!TYPE.equals(file.getContentType())) {
			return false;
		}
		return true;
	}

	@Override
	public Map<String, List<List<Object>>> extractExcelData(MultipartFile file) throws IOException {
		Map<String, List<List<Object>>> data = extractExcelData(file.getInputStream());
		return data;
	}

	private Map<String, List<List<Object>>> extractExcelData(InputStream inputStream) throws IOException {

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
					rowData.add(null);
					break;
				default:
					rowData.add(null);
					break;
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
				break;
			case "Bank":
				bankService.bulkUpload(entry.getValue());
				break;
			case "Chit":
				chitService.bulkUpload(entry.getValue());
				break;
			case "Fd":
				fdService.bulkUpload(entry.getValue());
				break;
			case "Mutual Fund":
				mfService.bulkUpload(entry.getValue());
				break;
			case "Job":
				jobService.bulkUpload(entry.getValue());
				break;
			case "Event":
				eventService.bulkUpload(entry.getValue());
				break;
			case "User":
				break;
			case "To do":
				todoService.bulkUpload(entry.getValue());
				break;
			}

		}
	}

}
