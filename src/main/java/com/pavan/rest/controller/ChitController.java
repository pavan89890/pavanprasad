package com.pavan.rest.controller;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pavan.beans.ApiResponse;
import com.pavan.beans.ChitBean;
import com.pavan.service.ChitService;
import com.pavan.util.Utility;

@RestController
@RequestMapping(path = "/api/chits")
@CrossOrigin("*")
public class ChitController {

	@Autowired
	private ChitService chitService;

	private String message;

	@PostMapping
	@Transactional(rollbackOn = Exception.class)
	public ApiResponse saveChit(@RequestBody(required = true) ChitBean chitBean) {

		try {
			chitService.saveChit(chitBean);

			if (Utility.isEmpty(chitBean.getId())) {
				message = "Chit saved successfully";
			} else {
				message = "Chit updated successfully";
			}

			return new ApiResponse(HttpStatus.OK, message, null);
		} catch (Exception e) {
			message = "Error-" + e.getMessage();
			return new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, message, null);
		}

	}

	@GetMapping
	public ApiResponse chits() {
		return chitService.getChits();
	}

	@GetMapping("/{id}")
	public ApiResponse getChit(@PathVariable(value = "id") Long id) {
		return chitService.getChit(id);
	}

	@DeleteMapping("/{id}")
	public ApiResponse deleteChit(@PathVariable(value = "id") Long id) {
		return chitService.deleteChit(id);
	}

	@DeleteMapping
	public ApiResponse deleteChits() {
		return chitService.deleteChits();
	}
}
