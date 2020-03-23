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

import com.pavan.service.DataDownloadService;

@RestController
@RequestMapping(path = "/api/downloaddata")
@CrossOrigin("*")
public class DataDownloadRestController {

	@Autowired
	private DataDownloadService dataDownloadService;

	@ResponseStatus(HttpStatus.OK)
	@GetMapping
	public HttpEntity<byte[]> downloadExcelReport() {

		/** assume that below line gives you file content in byte array **/
		byte[] excelContent = dataDownloadService.getDataFile();
		// prepare response
		HttpHeaders header = new HttpHeaders();
		header.setContentType(new MediaType("application", "vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
		header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=PavanPrasadData.xlsx");
		header.setContentLength(excelContent.length);

		return new HttpEntity<byte[]>(excelContent, header);
	}

}
