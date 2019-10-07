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
import com.pavan.beans.FdBean;
import com.pavan.service.FdService;
import com.pavan.util.Utility;

@RestController
@RequestMapping(path = "/api/fds")
@CrossOrigin("*")
public class FdController {

	@Autowired
	private FdService fdService;
	private String message;

	@PostMapping
	@Transactional(rollbackOn = Exception.class)
	public ApiResponse saveFd(@RequestBody(required = true) FdBean fdBean) {

		try {
			fdService.saveFd(fdBean);

			if (Utility.isEmpty(fdBean.getId())) {
				message = "Fd saved successfully";
			} else {
				message = "Fd updated successfully";
			}

			return new ApiResponse(HttpStatus.OK, message, null);
		} catch (Exception e) {
			message = "Error-" + e.getMessage();
			return new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, message, null);
		}

	}

	@GetMapping
	public ApiResponse fds() {
		return fdService.getFds();
	}

	@GetMapping("/{id}")
	public ApiResponse getFd(@PathVariable(value = "id") Long id) {
		return fdService.getFd(id);
	}

	@DeleteMapping("/{id}")
	public ApiResponse deleteFd(@PathVariable(value = "id") Long id) {
		return fdService.deleteFd(id);
	}

	@DeleteMapping()
	public ApiResponse deleteFds() {
		return fdService.deleteFds();
	}
}
