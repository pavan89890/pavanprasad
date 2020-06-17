package com.pavan.service;

import org.springframework.stereotype.Service;

import com.pavan.beans.ApiResponse;
import com.pavan.modal.User;

@Service
public interface AssetService {

	public ApiResponse getAssets(User currentUser);

}
