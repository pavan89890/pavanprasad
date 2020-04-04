package com.pavan.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pavan.modal.Bank;
import com.pavan.modal.Chit;
import com.pavan.modal.Expense;
import com.pavan.modal.Fd;
import com.pavan.modal.Job;
import com.pavan.modal.User;
import com.pavan.repository.BankRespository;
import com.pavan.repository.ChitRespository;
import com.pavan.repository.ExpenseRespository;
import com.pavan.repository.FdRespository;
import com.pavan.repository.JobRespository;
import com.pavan.repository.UserRespository;
import com.pavan.service.DataDownloadService;
import com.pavan.util.DateUtil;
import com.pavan.util.EmailUtil;
import com.pavan.util.ExcelUtil;
import com.pavan.util.Utility;

@Service
public class DataDownloadServiceImpl implements DataDownloadService {

	@Autowired
	private BankRespository bankRepository;

	@Autowired
	private ChitRespository chitRepository;

	@Autowired
	private ExpenseRespository expenseRepository;

	@Autowired
	private FdRespository fdRepository;

	@Autowired
	private JobRespository jobRepository;

	@Autowired
	private UserRespository userRepository;

	@Override
	public byte[] downloadData() {

		ByteArrayOutputStream bos = prepareData();

		byte[] bytes = bos.toByteArray();
		try {
			bos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bytes;
	}

	@Override
	public void mailData() throws Exception {
		ByteArrayOutputStream bos = prepareData();

		FileOutputStream fos = null;

		List<File> files = new ArrayList<File>();
		String fileName = "PavanPrasadData_" + DateUtil.fileCreationDateFormat.format(new Date()) + ".xlsx";
		File file = new File(fileName);
		files.add(file);

		fos = new FileOutputStream(file);
		bos.writeTo(fos);
		EmailUtil.sendMailWithAttachment("pavan89890@gmail.com", "Pavan Prasad Application Data Excel",
				"Hi Pavan,<br>Please find attached Pavan Prasad Application Data in excel format.", files);
		bos.close();
		fos.close();
		new Utility().deleteFiles(files);
	}

	private ByteArrayOutputStream prepareData() {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();

		XSSFWorkbook workbook = new XSSFWorkbook();

		generateBankSheet(workbook);
		generateFdSheet(workbook);
		generateChitSheet(workbook);
		generateExpenseSheet(workbook);
		generateJobSheet(workbook);
		generateUserSheet(workbook);

		try {
			workbook.write(bos);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bos;
	}

	private void generateBankSheet(XSSFWorkbook workbook) {
		// Create Bank sheet
		XSSFSheet sheet = workbook.createSheet("Bank");

		String headers[] = new String[] { "ID", "NAME", "BALANCE", "CREATED_ON", "UPDATED_ON" };

		CellStyle headerStyle = ExcelUtil.getHeaderStyle(workbook);

		ExcelUtil.createExcelHeader(sheet, headers, headerStyle);

		List<Object[]> data = new ArrayList<Object[]>();

		List<Bank> banks = bankRepository.findAll();

		for (Bank bank : banks) {
			data.add(new Object[] { bank.getId(), bank.getName(), bank.getBalance(), bank.getCreatedOn().toString(),
					bank.getUpdatedOn().toString() });
		}

		ExcelUtil.createExcelRows(sheet, data);
	}

	private void generateChitSheet(XSSFWorkbook workbook) {
		// Create Bank sheet
		XSSFSheet sheet = workbook.createSheet("Chit");

		String headers[] = new String[] { "ID", "MONTH", "YEAR", "ACTUAL_AMOUNT", "PAID_AMOUNT", "PROFIT", "CREATED_ON",
				"UPDATED_ON" };

		CellStyle headerStyle = ExcelUtil.getHeaderStyle(workbook);

		ExcelUtil.createExcelHeader(sheet, headers, headerStyle);

		List<Object[]> data = new ArrayList<Object[]>();

		List<Chit> chits = chitRepository.findAll();

		for (Chit chit : chits) {
			data.add(new Object[] { chit.getId(), chit.getMonth(), chit.getYear(), chit.getActualAmount(),
					chit.getPaidAmount(), chit.getProfit(), chit.getCreatedOn().toString(),
					chit.getUpdatedOn().toString() });
		}

		ExcelUtil.createExcelRows(sheet, data);
	}

	private void generateExpenseSheet(XSSFWorkbook workbook) {
		// Create Bank sheet
		XSSFSheet sheet = workbook.createSheet("Expense");

		String headers[] = new String[] { "ID", "NAME", "AMOUNT", "EXPENSE_DATE", "CREATED_ON", "UPDATED_ON" };

		CellStyle headerStyle = ExcelUtil.getHeaderStyle(workbook);

		ExcelUtil.createExcelHeader(sheet, headers, headerStyle);

		List<Object[]> data = new ArrayList<Object[]>();

		List<Expense> expenses = expenseRepository.findAll();

		for (Expense expense : expenses) {
			data.add(new Object[] { expense.getId(), expense.getName(), expense.getAmount(), expense.getDate(),
					expense.getCreatedOn().toString(), expense.getUpdatedOn().toString() });
		}

		ExcelUtil.createExcelRows(sheet, data);
	}

	private void generateFdSheet(XSSFWorkbook workbook) {
		// Create Bank sheet
		XSSFSheet sheet = workbook.createSheet("Fd");

		String headers[] = new String[] { "ID", "BANK", "DEP_AMOUNT", "ROI", "MAT_AMOUNT", "DEPOSITED_ON",
				"PERIOD_IN_MONTHS", "MATURED_ON", "CREATED_ON", "UPDATED_ON" };

		CellStyle headerStyle = ExcelUtil.getHeaderStyle(workbook);

		ExcelUtil.createExcelHeader(sheet, headers, headerStyle);

		List<Object[]> data = new ArrayList<Object[]>();

		List<Fd> fds = fdRepository.findAll();

		for (Fd fd : fds) {
			data.add(new Object[] { fd.getId(), fd.getBank(), fd.getDepAmount(), fd.getRoi(), fd.getMaturedAmount(),
					fd.getDepositedOn(), fd.getPeriodInMonths(), fd.getMaturedOn(), fd.getCreatedOn().toString(),
					fd.getUpdatedOn().toString() });
		}

		ExcelUtil.createExcelRows(sheet, data);
	}

	private void generateJobSheet(XSSFWorkbook workbook) {
		// Create Job sheet
		XSSFSheet sheet = workbook.createSheet("Job");

		String headers[] = new String[] { "ID", "COMPANY", "DESIGNATION", "DOJ", "DOL", "CURRENT_JOB", "CREATED_ON",
				"UPDATED_ON" };

		CellStyle headerStyle = ExcelUtil.getHeaderStyle(workbook);

		ExcelUtil.createExcelHeader(sheet, headers, headerStyle);

		List<Object[]> data = new ArrayList<Object[]>();

		List<Job> jobs = jobRepository.findAll();

		for (Job job : jobs) {
			data.add(new Object[] { job.getId(), job.getCompany(), job.getDesignation(), job.getDoj(), job.getDol(),
					job.getCurrent(), job.getCreatedOn().toString(), job.getUpdatedOn().toString() });
		}

		ExcelUtil.createExcelRows(sheet, data);
	}

	private void generateUserSheet(XSSFWorkbook workbook) {
		// Create Job sheet
		XSSFSheet sheet = workbook.createSheet("User");

		String headers[] = new String[] { "ID", "NAME", "MOBILE", "ORI_DOB", "CER_DOB", "CREATED_ON", "UPDATED_ON" };

		CellStyle headerStyle = ExcelUtil.getHeaderStyle(workbook);

		ExcelUtil.createExcelHeader(sheet, headers, headerStyle);

		List<Object[]> data = new ArrayList<Object[]>();

		List<User> users = userRepository.findAll();

		for (User user : users) {
			data.add(new Object[] { user.getId(), user.getName(), user.getMobile(), user.getOriDob(), user.getCerDob(),
					user.getCreatedOn().toString(), user.getUpdatedOn().toString() });
		}

		ExcelUtil.createExcelRows(sheet, data);
	}
}