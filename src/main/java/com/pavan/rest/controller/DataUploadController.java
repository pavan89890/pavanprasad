package com.pavan.rest.controller;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.pavan.modal.User;
import com.pavan.service.DataUploadService;
import com.pavan.service.UserService;

import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ResponseMessage;

@RestController
@RequestMapping(path = "/api/data/")
@CrossOrigin("*")
public class DataUploadController {

	@Autowired
	private DataUploadService dataUploadService;

	@Autowired
	private UserService userService;

	@PostMapping("/upload")
	@Transactional(rollbackOn = Exception.class)
	public ResponseEntity<ResponseMessage> uploadFile(@RequestHeader("userToken") String userToken,
			@RequestParam("file") MultipartFile file) {

		User currentUser = userService.getUserFromToken(userToken);

		if (currentUser != null) {

			String message = "";
			if (dataUploadService.hasExcelFormat(file)) {
				try {

					Map<String, List<List<Object>>> data = dataUploadService.extractExcelData(file);
					dataUploadService.uploadData(data);

					message = "Uploaded the file successfully: " + file.getOriginalFilename();
					return ResponseEntity.status(HttpStatus.OK)
							.body(new ResponseMessageBuilder().message(message).build());
				} catch (Exception e) {
					message = "Could not upload the file: " + file.getOriginalFilename() + "!";
					return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
							.body(new ResponseMessageBuilder().message(message).build());
				}
			}
			message = "Please upload an excel file!";
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ResponseMessageBuilder().message(message).build());

		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(new ResponseMessageBuilder().message("Unauthorized to acccess this resource").build());
		}

	}

}
