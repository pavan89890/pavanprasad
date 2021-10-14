package com.pavan.service.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.pavan.beans.ApiResponse;
import com.pavan.modal.User;
import com.pavan.repository.BankRepository;
import com.pavan.repository.ChitRepository;
import com.pavan.repository.FdRespository;
import com.pavan.repository.MfRespository;
import com.pavan.service.AssetService;
import com.pavan.util.Utility;

@Service
public class AssetServiceImpl implements AssetService {

	@Autowired
	BankRepository bankRepository;

	@Autowired
	FdRespository fdRepository;

	@Autowired
	MfRespository mfRepository;

	@Autowired
	ChitRepository chitRepository;

	@Override
	public ApiResponse getAssets(User currentUser) {

		Map<String, Object> data = new LinkedHashMap<>();

		Float bankBalance = bankRepository.getTotalBalance(currentUser);
		Float bankBalanceWithoutPPF = bankRepository.getBankBalanceWithoutPPF(currentUser);
		Float chitBalance = chitRepository.getTotalDeposited(currentUser);
		chitBalance = null; // Chit is closed
		Float fdBalance = fdRepository.getTotalDeposited(currentUser);
		Float mfBalance = mfRepository.getTotalCurrent(currentUser);

		Float totalBalance = (bankBalance != null ? bankBalance : 0f) + (chitBalance != null ? chitBalance : 0f)
				+ (fdBalance != null ? fdBalance : 0f) + (mfBalance != null ? mfBalance : 0f);

		Float totalGross = (bankBalanceWithoutPPF != null ? bankBalanceWithoutPPF : 0f)
				+ (chitBalance != null ? chitBalance : 0f) + (fdBalance != null ? fdBalance : 0f);

		data.put("bankBalance", bankBalance != null ? Utility.formatNumber(bankBalance) : 0f);
		data.put("chitBalance", chitBalance != null ? Utility.formatNumber(chitBalance) : 0f);
		data.put("fdBalance", fdBalance != null ? Utility.formatNumber(fdBalance) : 0f);
		data.put("mfBalance", mfBalance != null ? Utility.formatNumber(mfBalance) : 0f);
		data.put("totalGross", Utility.formatNumber(totalGross));
		data.put("totalBalance", Utility.formatNumber(totalBalance));

		return new ApiResponse(HttpStatus.OK, null, data);
	}

}