package com.pavan.service;

import org.springframework.stereotype.Service;

import com.pavan.beans.ApiResponse;
import com.pavan.beans.UserBean;
import com.pavan.modal.User;

@Service
public interface UserService {

	public void saveUser(UserBean user) throws Exception;

	public ApiResponse getUsers();

	public ApiResponse getUser(Long id);

	public ApiResponse deleteUser(Long id);

	public ApiResponse deleteUsers();

	public User getUserFromToken(String userToken);

	public UserBean getUser(String username, String password);

	public ApiResponse getUserResponseFromToken(String userToken);

}
