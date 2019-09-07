package com.pavan.service;

import org.springframework.stereotype.Service;

import com.pavan.beans.ApiResponse;
import com.pavan.modal.User;

@Service
public interface UserService {

	public ApiResponse saveUser(User user);

	public ApiResponse getUsers();

	public ApiResponse getUser(Long id);

	public ApiResponse deleteUser(Long id);

	public ApiResponse deleteUsers();

}
