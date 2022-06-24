package com.pavan.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface DataUploadService {

	public boolean hasExcelFormat(MultipartFile file);

	public Map<String, List<List<Object>>> extractExcelData(MultipartFile file) throws IOException;

	public void uploadData(Map<String, List<List<Object>>> data);

}
