package com.pavan.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.pavan.beans.ApiResponse;
import com.pavan.modal.User;
import com.pavan.repository.UserRespository;
import com.pavan.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRespository usersRepository;

	private String message = "";

	@Override
	public ApiResponse getUsers() {
		if (usersRepository.findAll() == null) {
			return new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, "No data found", null);
		}
		return new ApiResponse(HttpStatus.OK, null, usersRepository.findAll());
	}

	@Override
	public ApiResponse saveUser(User user) {
		User c = usersRepository.findByMobile(user.getMobile());

		if (c != null) {
			if ((user.getId() == null) || (user.getId() != c.getId())) {
				return new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Mobile Number Already Exists", null);
			}
		}

		if (user.getId() == null && user.getId() == 0) {
			message = "User saved successfully";
		} else {
			message = "User updated successfully";
		}
		usersRepository.save(user);

		return new ApiResponse(HttpStatus.OK, message, null);
	}

	@Override
	public ApiResponse getUser(Long id) {
		if (id == null || id == 0) {
			return new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, "No data found", null);
		} else {
			if (usersRepository.getOne(id) != null) {
				return new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, null, usersRepository.getOne(id));
			} else {
				return new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, "No data found", null);
			}
		}
	}

	@Override
	public ApiResponse deleteUser(Long id) {
		if (getUser(id).getData() == null) {
			return new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, "No data found", null);
		} else {
			usersRepository.delete((User) getUser(id).getData());
			message = "User deleted successfully";
			return new ApiResponse(HttpStatus.OK, message, null);
		}
	}

}
