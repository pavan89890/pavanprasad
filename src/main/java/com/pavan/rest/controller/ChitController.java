package com.pavan.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.pavan.modal.Chit;
import com.pavan.service.ChitService;

@RestController
@RequestMapping(path = "/chits/")
@CrossOrigin("*")
public class ChitController {

	@Autowired
	private ChitService chitService;

	@PostMapping
	public ApiResponse saveChit(@RequestBody(required = true) ChitBean chitBean) {

		Chit chit = new Chit();
		if (chitBean.getId() != null) {
			chit.setId(chitBean.getId());
		}
		chit.setMonth(chitBean.getMonth());
		chit.setYear(chitBean.getYear());
		chit.setActualAmount(chitBean.getActualAmount());
		chit.setPaidAmount(chitBean.getPaidAmount());
		chit.setProfit(chit.getActualAmount()-chitBean.getPaidAmount());

		return chitService.saveChit(chit);

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
