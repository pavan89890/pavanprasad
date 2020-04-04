package com.pavan.service;

import org.springframework.stereotype.Service;

@Service
public interface DataDownloadService {

	public byte[] downloadData();

	public void mailData() throws Exception;

}
