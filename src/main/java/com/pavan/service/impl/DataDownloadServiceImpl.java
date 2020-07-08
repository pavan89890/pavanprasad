package com.pavan.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
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
import com.pavan.modal.Events;
import com.pavan.modal.Expense;
import com.pavan.modal.Fd;
import com.pavan.modal.Job;
import com.pavan.modal.MutualFund;
import com.pavan.modal.User;
import com.pavan.repository.BankRepository;
import com.pavan.repository.ChitRepository;
import com.pavan.repository.EventRepository;
import com.pavan.repository.ExpenseRepository;
import com.pavan.repository.FdRespository;
import com.pavan.repository.JobRespository;
import com.pavan.repository.MfRespository;
import com.pavan.repository.UserRespository;
import com.pavan.service.DataDownloadService;
import com.pavan.util.DateUtil;
import com.pavan.util.EmailUtil;
import com.pavan.util.ExcelUtil;
import com.pavan.util.Utility;

@Service
public class DataDownloadServiceImpl implements DataDownloadService {

	@Autowired
	private BankRepository bankRepository;

	@Autowired
	private ChitRepository chitRepository;

	@Autowired
	private ExpenseRepository expenseRepository;

	@Autowired
	private FdRespository fdRepository;

	@Autowired
	private JobRespository jobRepository;

	@Autowired
	private UserRespository userRepository;

	@Autowired
	private MfRespository mfRepository;

	@Autowired
	private EventRepository eventRepository;

	@Override
	public byte[] downloadData() {
		byte[] bytes = null;
		try {
			ByteArrayOutputStream bos = prepareData();

			bytes = bos.toByteArray();

			bos.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
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

	private ByteArrayOutputStream prepareData() throws Exception {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();

		XSSFWorkbook workbook = new XSSFWorkbook();

		generateExpenseSheet(workbook);
		generateBankSheet(workbook);
		generateChitSheet(workbook);
		generateFdSheet(workbook);
		generateMfSheet(workbook);
		generateJobSheet(workbook);
		generateEventSheet(workbook);
		generateUserSheet(workbook);

		workbook.write(bos);

		return bos;
	}

	private void generateEventSheet(XSSFWorkbook workbook) {
		// Create Bank sheet
		XSSFSheet sheet = workbook.createSheet("Event");

		String headers[] = new String[] { "ID", "USER_ID", "EVENT_TYPE", "EVENT_NAME", "EVENT_DATE", "CREATED_ON",
				"UPDATED_ON" };

		CellStyle headerStyle = ExcelUtil.getHeaderStyle(workbook);

		ExcelUtil.createExcelHeader(sheet, headers, headerStyle);

		List<Object[]> data = new ArrayList<Object[]>();

		List<Events> events = eventRepository.findAll();

		for (Events event : events) {
			data.add(
					new Object[] { event.getId(), event.getUser() != null ? event.getUser().getId() : null, event.getEventType(),
							event.getEventName(),DateUtil.dateToStr(event.getEventDate()), event.getCreatedOn() != null ? event.getCreatedOn().toString() : null,
							event.getUpdatedOn() != null ? event.getUpdatedOn().toString() : null });
		}

		ExcelUtil.createExcelRows(sheet, data);
	}

	private void generateBankSheet(XSSFWorkbook workbook) {
		// Create Bank sheet
		XSSFSheet sheet = workbook.createSheet("Bank");

		String headers[] = new String[] { "ID", "USER_ID", "NAME", "BALANCE", "CREATED_ON", "UPDATED_ON" };

		CellStyle headerStyle = ExcelUtil.getHeaderStyle(workbook);

		ExcelUtil.createExcelHeader(sheet, headers, headerStyle);

		List<Object[]> data = new ArrayList<Object[]>();

		List<Bank> banks = bankRepository.findAll();

		for (Bank bank : banks) {
			data.add(
					new Object[] { bank.getId(), bank.getUser() != null ? bank.getUser().getId() : null, bank.getName(),
							bank.getBalance(), bank.getCreatedOn() != null ? bank.getCreatedOn().toString() : null,
							bank.getUpdatedOn() != null ? bank.getUpdatedOn().toString() : null });
		}

		ExcelUtil.createExcelRows(sheet, data);
	}

	private void generateChitSheet(XSSFWorkbook workbook) {
		// Create Bank sheet
		XSSFSheet sheet = workbook.createSheet("Chit");

		String headers[] = new String[] { "ID", "USER_ID", "MONTH", "YEAR", "ACTUAL_AMOUNT", "PAID_AMOUNT", "PROFIT",
				"CREATED_ON", "UPDATED_ON" };

		CellStyle headerStyle = ExcelUtil.getHeaderStyle(workbook);

		ExcelUtil.createExcelHeader(sheet, headers, headerStyle);

		List<Object[]> data = new ArrayList<Object[]>();

		List<Chit> chits = chitRepository.findAll();

		for (Chit chit : chits) {
			data.add(new Object[] { chit.getId(), chit.getUser() != null ? chit.getUser().getId() : null,
					chit.getMonth(), chit.getYear(), chit.getActualAmount(), chit.getPaidAmount(), chit.getProfit(),
					chit.getCreatedOn() != null ? chit.getCreatedOn().toString() : null,
					chit.getUpdatedOn() != null ? chit.getUpdatedOn().toString() : null });
		}

		ExcelUtil.createExcelRows(sheet, data);
	}

	private void generateExpenseSheet(XSSFWorkbook workbook) {
		// Create Bank sheet
		XSSFSheet sheet = workbook.createSheet("Expense");

		String headers[] = new String[] { "ID", "USER_ID", "EXPENSE_TYPE", "NAME", "AMOUNT", "EXPENSE_DATE",
				"CREATED_ON", "UPDATED_ON" };

		CellStyle headerStyle = ExcelUtil.getHeaderStyle(workbook);

		ExcelUtil.createExcelHeader(sheet, headers, headerStyle);

		List<Object[]> data = new ArrayList<Object[]>();

		List<Expense> expenses = expenseRepository.findAll();

		for (Expense expense : expenses) {
			data.add(new Object[] { expense.getId(), expense.getUser() != null ? expense.getUser().getId() : null,
					expense.getExpenseType(), expense.getName(), expense.getAmount(),
					DateUtil.dateToStr(expense.getDate()),
					expense.getCreatedOn() != null ? expense.getCreatedOn().toString() : null,
					expense.getUpdatedOn() != null ? expense.getUpdatedOn().toString() : null });
		}

		ExcelUtil.createExcelRows(sheet, data);
	}

	private void generateFdSheet(XSSFWorkbook workbook) {
		// Create Bank sheet
		XSSFSheet sheet = workbook.createSheet("Fd");

		String headers[] = new String[] { "ID", "USER_ID", "BANK", "DEP_AMOUNT", "ROI", "MAT_AMOUNT", "DEPOSITED_ON",
				"PERIOD_IN_MONTHS", "MATURED_ON", "CREATED_ON", "UPDATED_ON" };

		CellStyle headerStyle = ExcelUtil.getHeaderStyle(workbook);

		ExcelUtil.createExcelHeader(sheet, headers, headerStyle);

		List<Object[]> data = new ArrayList<Object[]>();

		List<Fd> fds = fdRepository.findAll();

		for (Fd fd : fds) {
			data.add(new Object[] { fd.getId(), fd.getUser() != null ? fd.getUser().getId() : null, fd.getBank(),
					fd.getDepAmount(), fd.getRoi(), fd.getMaturedAmount(), DateUtil.dateToStr(fd.getDepositedOn()),
					fd.getPeriodInMonths(), DateUtil.dateToStr(fd.getMaturedOn()),
					fd.getCreatedOn() != null ? fd.getCreatedOn().toString() : null,
					fd.getUpdatedOn() != null ? fd.getUpdatedOn().toString() : null });
		}

		ExcelUtil.createExcelRows(sheet, data);
	}

	private void generateJobSheet(XSSFWorkbook workbook) {
		// Create Job sheet
		XSSFSheet sheet = workbook.createSheet("Job");

		String headers[] = new String[] { "ID", "USER_ID", "COMPANY", "DESIGNATION", "DOJ", "DOL", "CURRENT_JOB",
				"CREATED_ON", "UPDATED_ON" };

		CellStyle headerStyle = ExcelUtil.getHeaderStyle(workbook);

		ExcelUtil.createExcelHeader(sheet, headers, headerStyle);

		List<Object[]> data = new ArrayList<Object[]>();

		List<Job> jobs = jobRepository.findAll();

		for (Job job : jobs) {
			data.add(new Object[] { job.getId(), job.getUser() != null ? job.getUser().getId() : null, job.getCompany(),
					job.getDesignation(), DateUtil.dateToStr(job.getDoj()), DateUtil.dateToStr(job.getDol()),
					job.getCurrent(), job.getCreatedOn() != null ? job.getCreatedOn().toString() : null,
					job.getUpdatedOn() != null ? job.getUpdatedOn().toString() : null });
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
			data.add(new Object[] { user.getId(), user.getName(), user.getMobile(),
					DateUtil.dateToStr(user.getOriDob()), DateUtil.dateToStr(user.getCerDob()),
					user.getCreatedOn() != null ? user.getCreatedOn().toString() : null,
					user.getUpdatedOn() != null ? user.getUpdatedOn().toString() : null });
		}

		ExcelUtil.createExcelRows(sheet, data);
	}

	private void generateMfSheet(XSSFWorkbook workbook) {
		// Create Bank sheet
		XSSFSheet sheet = workbook.createSheet("Mutual Fund");

		String headers[] = new String[] { "ID", "USER_ID", "NAME", "DESCRIPTION", "INVESTED_AMOUNT", "CURRENT_AMOUNT",
				"DEPOSITED_ON", "CREATED_ON", "UPDATED_ON" };

		CellStyle headerStyle = ExcelUtil.getHeaderStyle(workbook);

		ExcelUtil.createExcelHeader(sheet, headers, headerStyle);

		List<Object[]> data = new ArrayList<Object[]>();

		List<MutualFund> mfs = mfRepository.findAll();

		for (MutualFund mf : mfs) {
			data.add(new Object[] { mf.getId(), mf.getUser() != null ? mf.getUser().getId() : null, mf.getName(),
					mf.getDesc(), mf.getInvestedAmount(), mf.getCurrentAmount(),
					DateUtil.dateToStr(mf.getDepositedOn()),
					mf.getCreatedOn() != null ? mf.getCreatedOn().toString() : null,
					mf.getUpdatedOn() != null ? mf.getUpdatedOn().toString() : null });
		}

		ExcelUtil.createExcelRows(sheet, data);
	}
}