package com.pavan.service.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.pavan.beans.ApiResponse;
import com.pavan.beans.BankBean;
import com.pavan.modal.Bank;
import com.pavan.modal.User;
import com.pavan.repository.BankRespository;
import com.pavan.service.BankService;
import com.pavan.util.Utility;

@Service
public class BankServiceImpl implements BankService {

	@Autowired
	BankRespository bankRepository;

	private String message = "";

	@Override
	public ApiResponse getBanks() {

		Map<String, Object> data = new LinkedHashMap<>();

		List<Bank> banks = bankRepository.getBanksOrderByBalDesc();

		if (Utility.isEmpty(banks)) {
			return new ApiResponse(HttpStatus.NO_CONTENT, "No data found", null);
		}

		Float totalBalance = bankRepository.getTotalBalance();

		data.put("banks", banks);
		data.put("totalBalance", totalBalance);

		return new ApiResponse(HttpStatus.OK, null, data);
	}

	@Override
	public ApiResponse getBanks(User currentUser) {

		Map<String, Object> data = new LinkedHashMap<>();

		List<Bank> banks = bankRepository.getBanksOrderByBalDesc();

		if (Utility.isEmpty(banks)) {
			return new ApiResponse(HttpStatus.NO_CONTENT, "No data found", null);
		}

		Float totalBalance = bankRepository.getTotalBalance();

		data.put("banks", banks);
		data.put("totalBalance", totalBalance);

		return new ApiResponse(HttpStatus.OK, null, data);
	}

	@Override
	public void saveBank(BankBean bankBean) throws Exception {

		if (!validData(bankBean)) {
			throw new Exception(message);
		}

		Bank bank = new Bank();
		if (bankBean.getId() != null) {
			bank.setId(bankBean.getId());
		}
		bank.setName(bankBean.getName());
		bank.setBalance(bankBean.getBalance());

		Bank c = bankRepository.findByName(bank.getName());

		if (c != null) {
			if ((bank.getId() == null) || (bank.getId() != c.getId())) {
				message = "Bank Name Already Exists";
				throw new Exception(message);
			}
		}

		bankRepository.save(bank);

	}

	private boolean validData(BankBean bean) {

		if (Utility.isEmpty(bean.getName())) {
			message = "Please Enter Name";
			return false;
		}

		if (bean.getBalance() == null) {
			message = "Please Enter Balance";
			return false;
		}

		return true;
	}

	@Override
	public ApiResponse getBank(Long id) {
		if (id == null || id == 0) {
			return new ApiResponse(HttpStatus.NO_CONTENT, "No data found", null);
		} else {

			Optional<Bank> bankOp = bankRepository.findById(id);
			if (bankOp.isPresent()) {
				Bank bank = bankOp.get();
				return new ApiResponse(HttpStatus.OK, null, bank);
			} else {
				return new ApiResponse(HttpStatus.NO_CONTENT, "No data found", null);
			}
		}
	}

	@Override
	public ApiResponse deleteBank(Long id) {
		if (getBank(id).getData() == null) {
			return new ApiResponse(HttpStatus.NO_CONTENT, "No data found", null);
		} else {
			bankRepository.delete((Bank) getBank(id).getData());
			message = "Bank deleted successfully";
			return new ApiResponse(HttpStatus.OK, message, null);
		}
	}

	@Override
	public ApiResponse deleteBanks() {
		bankRepository.deleteAll();
		message = "Banks deleted successfully";
		return new ApiResponse(HttpStatus.OK, message, null);
	}

}
