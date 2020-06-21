package com.pavan.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pavan.beans.ApiResponse;
import com.pavan.modal.User;
import com.pavan.service.DataDownloadService;
import com.pavan.service.LoginService;

@RestController
@RequestMapping(path = "/api/data/")
@CrossOrigin("*")
public class DataDownloadController {

	@Autowired
	private DataDownloadService dataDownloadService;

	private String message;

	@Autowired
	private LoginService loginService;

	@GetMapping("download")
	public HttpEntity<byte[]> downloadData(@RequestHeader("userToken") String userToken) {

		User currentUser = loginService.getUserFromToken(userToken);

		HttpHeaders header = new HttpHeaders();
		header.setContentType(new MediaType("application", "vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
		header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=PavanPrasadData.xlsx");

		byte[] excelContent = null;

		if (currentUser != null) {
			/** assume that below line gives you file content in byte array **/
			excelContent = dataDownloadService.downloadData();
			// prepare response

			header.setContentLength(excelContent != null ? excelContent.length : 0);

			return new HttpEntity<byte[]>(excelContent, header);
		} else {
			header.setContentLength(excelContent != null ? excelContent.length : 0);
			return new HttpEntity<byte[]>(excelContent, header);
		}
	}

	@GetMapping("mail")
	public ApiResponse mailData(@RequestHeader("userToken") String userToken) {

		try {
			User currentUser = loginService.getUserFromToken(userToken);
			if (currentUser != null) {
				dataDownloadService.mailData();

				message = "Email sent successfully";

				return new ApiResponse(HttpStatus.OK, message, null);
			} else {
				return new ApiResponse(HttpStatus.UNAUTHORIZED, "Unauthorized to acccess this resource", null);
			}

		} catch (Exception e) {
			message = "Error-" + e.getMessage();
			return new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, message, null);
		}
	}

}
