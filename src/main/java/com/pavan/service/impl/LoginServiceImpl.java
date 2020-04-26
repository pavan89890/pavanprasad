package com.pavan.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.pavan.beans.ApiResponse;
import com.pavan.beans.UserBean;
import com.pavan.modal.User;
import com.pavan.repository.UserRespository;
import com.pavan.service.LoginService;
import com.pavan.util.Utility;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	UserRespository userRepository;

	@Override
	public ApiResponse login(String username, String password) {

		User user = userRepository.findByEmailAndPassword(username, password);

		UserBean userBean = toUserBean(user);

		String message = "";
		if (user == null) {
			message = "Invalid Credentials";
		} else {
			message = "Successfully logged in";
		}
		return new ApiResponse(HttpStatus.OK, message, userBean);
	}

	private UserBean toUserBean(User user) {
		if (user != null) {
			UserBean userBean = new UserBean();
			userBean.setId(user.getId());
			userBean.setEmail(user.getEmail());
			userBean.setMobile(user.getMobile());
			userBean.setName(user.getName());
			userBean.setUserToken(Utility.encrypt(user.getId() + ""));
			return userBean;
		}
		return null;
	}

	@Override
	public User getUserFromToken(String userToken) {
		if (userToken != null && userToken.length() > 0) {
			try {
				Long userId = Long.parseLong(Utility.decrypt(userToken));
				return userRepository.findById(userId).orElse(null);
			} catch (Exception e) {
				return null;
			}
		}
		return null;
	}

}