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
		Float chitBalance = chitRepository.getTotalDeposited(currentUser);
		Float fdBalance = fdRepository.getTotalDeposited(currentUser);
		Float mfBalance = mfRepository.getTotalCurrent(currentUser);

		Float totalBalance = (bankBalance != null ? bankBalance : 0f) + (chitBalance != null ? chitBalance : 0f)
				+ (fdBalance != null ? fdBalance : 0f) + (mfBalance != null ? mfBalance : 0f);

		data.put("bankBalance", bankBalance != null ? bankBalance : 0f);
		data.put("chitBalance", chitBalance != null ? chitBalance : 0f);
		data.put("fdBalance", fdBalance != null ? fdBalance : 0f);
		data.put("mfBalance", mfBalance != null ? mfBalance : 0f);
		data.put("totalBalance", totalBalance);

		return new ApiResponse(HttpStatus.OK, null, data);
	}

}