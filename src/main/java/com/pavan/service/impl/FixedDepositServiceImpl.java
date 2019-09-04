package com.pavan.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.pavan.beans.ApiResponse;
import com.pavan.modal.FixedDeposit;
import com.pavan.repository.FixedDepositRespository;
import com.pavan.service.FixedDepositService;

@Service
public class FixedDepositServiceImpl implements FixedDepositService {

	@Autowired
	FixedDepositRespository fixedDepositsRepository;

	private String message = "";

	@Override
	public ApiResponse saveFixedDeposit(FixedDeposit fixedDeposit) {

		if (fixedDeposit.getId() == null || fixedDeposit.getId() == 0) {
			message = "Fixed Deposit saved successfully";
		} else {
			message = "Fixed Deposit updated successfully";
		}
		fixedDepositsRepository.save(fixedDeposit);

		return new ApiResponse(HttpStatus.OK, message, null);
	}

	@Override
	public ApiResponse getFixedDeposits() {
		if (fixedDepositsRepository.findAll() == null) {
			return new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, "No data found", null);
		}
		return new ApiResponse(HttpStatus.OK, null, fixedDepositsRepository.findAll());
	}

	@Override
	public ApiResponse getFixedDeposit(Long id) {
		if (id == null || id == 0) {
			return new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, "No data found", null);
		} else {

			Optional<FixedDeposit> fdOp = fixedDepositsRepository.findById(id);
			if (fdOp.isPresent()) {
				FixedDeposit fd = fdOp.get();
				return new ApiResponse(HttpStatus.OK, null, fd);
			} else {
				return new ApiResponse(HttpStatus.NO_CONTENT, "No data found", null);
			}
		}
	}

	@Override
	public ApiResponse deleteFixedDeposit(Long id) {
		if (getFixedDeposit(id).getData() == null) {
			return new ApiResponse(HttpStatus.NO_CONTENT, "No data found", null);
		} else {
			fixedDepositsRepository.delete((FixedDeposit) getFixedDeposit(id).getData());
			message = "FixedDeposit deleted successfully";
			return new ApiResponse(HttpStatus.OK, message, null);
		}
	}

}
