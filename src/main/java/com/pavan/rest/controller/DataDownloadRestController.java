package com.pavan.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.pavan.beans.ApiResponse;
import com.pavan.service.DataDownloadService;

@RestController
@RequestMapping(path = "/api/data/")
@CrossOrigin("*")
public class DataDownloadRestController {

	@Autowired
	private DataDownloadService dataDownloadService;

	private String message;

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("download")
	public HttpEntity<byte[]> downloadData() {

		/** assume that below line gives you file content in byte array **/
		byte[] excelContent = dataDownloadService.downloadData();
		// prepare response
		HttpHeaders header = new HttpHeaders();
		header.setContentType(new MediaType("application", "vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
		header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=PavanPrasadData.xlsx");
		header.setContentLength(excelContent.length);

		return new HttpEntity<byte[]>(excelContent, header);
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("mail")
	public ApiResponse mailData() {

		try {
			dataDownloadService.mailData();

			message = "Email sent successfully";

			return new ApiResponse(HttpStatus.OK, message, null);
		} catch (Exception e) {
			message = "Error-" + e.getMessage();
			return new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, message, null);
		}
	}

}
