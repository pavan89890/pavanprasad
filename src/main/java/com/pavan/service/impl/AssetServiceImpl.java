package com.pavan.service.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.pavan.beans.ApiResponse;
import com.pavan.repository.BankRespository;
import com.pavan.repository.ChitRespository;
import com.pavan.repository.FdRespository;
import com.pavan.service.AssetService;

@Service
public class AssetServiceImpl implements AssetService {

	@Autowired
	BankRespository bankRepository;

	@Autowired
	FdRespository fdRepository;

	@Autowired
	ChitRespository chitRepository;

	@Override
	public ApiResponse getAssets() {

		Map<String, Object> data = new LinkedHashMap<>();

		Float bankBalance = bankRepository.getTotalBalance();
		Float chitBalance = chitRepository.getTotalDeposited();
		Float fdBalance = fdRepository.getTotalDeposited();
		Float totalBalance = bankBalance + chitBalance + fdBalance;

		data.put("bankBalance", bankBalance);
		data.put("chitBalance", chitBalance);
		data.put("fdBalance", fdBalance);
		data.put("totalBalance", totalBalance);

		return new ApiResponse(HttpStatus.OK, null, data);
	}

}