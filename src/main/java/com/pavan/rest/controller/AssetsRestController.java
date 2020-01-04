package com.pavan.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pavan.beans.ApiResponse;
import com.pavan.service.AssetService;

@RestController
@RequestMapping(path = "/api/assets")
@CrossOrigin("*")
public class AssetsRestController {

	@Autowired
	private AssetService assetService;

	@GetMapping
	public ApiResponse assets() {
		return assetService.getAssets();
	}

}
