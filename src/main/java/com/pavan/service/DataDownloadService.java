package com.pavan.service;

import org.springframework.stereotype.Service;

@Service
public interface DataDownloadService {

	public byte[] getDataFile();

}
