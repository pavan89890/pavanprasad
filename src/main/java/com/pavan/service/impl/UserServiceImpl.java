package com.pavan.service.impl;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.pavan.beans.ApiResponse;
import com.pavan.beans.UserBean;
import com.pavan.modal.User;
import com.pavan.repository.UserRespository;
import com.pavan.service.UserService;
import com.pavan.util.DateUtil;
import com.pavan.util.Utility;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRespository usersRepository;

	private String message = "";

	@Override
	public ApiResponse getUsers() {
		List<User> users = usersRepository.findAll();

		if (users == null) {
			return new ApiResponse(HttpStatus.NOT_FOUND, "No data found", null);
		}

		List<UserBean> userBeans = new ArrayList<UserBean>();

		for (User user : users) {
			UserBean bean = new UserBean();
			bean.setId(user.getId());
			bean.setName(user.getName());
			bean.setEmail(user.getEmail());
			bean.setPassword(user.getPassword());
			bean.setMobile(user.getMobile());

			LocalDate currentDate = LocalDate.now();

			if (user.getOriDob() != null) {
				bean.setOriDobStr(DateUtil.yyyy_MM_dd.format(user.getOriDob()));
				LocalDate oriLocalDate = user.getOriDob().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				bean.setOriAgeStr(DateUtil.getDateDifference(oriLocalDate, currentDate));
			}

			if (user.getCerDob() != null) {
				bean.setCerDobStr(DateUtil.yyyy_MM_dd.format(user.getCerDob()));
				LocalDate cerLocalDate = user.getCerDob().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				bean.setCerAgeStr(DateUtil.getDateDifference(cerLocalDate, currentDate));
			}

			userBeans.add(bean);
		}

		return new ApiResponse(HttpStatus.OK, null, userBeans);
	}

	@Override
	public void saveUser(UserBean userBean) throws Exception {

		if (!validData(userBean)) {
			throw new Exception(message);
		}

		User user = new User();
		if (userBean.getId() != null) {
			user.setId(userBean.getId());
		}
		user.setName(userBean.getName());
		user.setEmail(userBean.getEmail());
		user.setMobile(userBean.getMobile());
		user.setPassword(userBean.getPassword());

		Date oriDob = null, cerDob = null;

		if (!Utility.isEmpty(userBean.getOriDobStr())) {
			try {
				oriDob = DateUtil.yyyy_MM_dd.parse(userBean.getOriDobStr());
				user.setOriDob(oriDob);
			} catch (ParseException e) {
				message = e.getMessage();
				throw new Exception(message);
			}
		}

		if (!Utility.isEmpty(userBean.getCerDobStr())) {
			try {
				cerDob = DateUtil.yyyy_MM_dd.parse(userBean.getCerDobStr());
				user.setCerDob(cerDob);
			} catch (ParseException e) {
				message = e.getMessage();
				throw new Exception(message);
			}
		}

		User c = usersRepository.findByMobile(user.getMobile());

		if (c != null) {
			if ((user.getId() == null) || (user.getId() != c.getId())) {
				message = "Mobile Number Already Exists";
				throw new Exception(message);
			}
		}

		usersRepository.save(user);

	}

	private boolean validData(UserBean bean) {

		if (Utility.isEmpty(bean.getName())) {
			message = "Please Enter Name";
			return false;
		}

		if (Utility.isEmpty(bean.getMobile())) {
			message = "Please Enter Mobile";
			return false;
		}
		
		if (Utility.isEmpty(bean.getPassword())) {
			message = "Please Enter Password";
			return false;
		}

		return true;
	}

	@Override
	public ApiResponse getUser(Long id) {
		if (id == null || id == 0) {
			return new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, "No data found", null);
		} else {

			Optional<User> userOp = usersRepository.findById(id);
			if (userOp.isPresent()) {
				User user = userOp.get();

				UserBean bean = new UserBean();
				bean.setId(user.getId());
				bean.setName(user.getName());
				bean.setMobile(user.getMobile());
				bean.setOriDobStr(DateUtil.yyyy_MM_dd.format(user.getOriDob()));
				bean.setCerDobStr(DateUtil.yyyy_MM_dd.format(user.getCerDob()));

				LocalDate currentDate = LocalDate.now();
				LocalDate oriLocalDate = user.getOriDob().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				LocalDate cerLocalDate = user.getCerDob().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

				bean.setOriAgeStr(DateUtil.getDateDifference(currentDate, oriLocalDate));
				bean.setCerAgeStr(DateUtil.getDateDifference(currentDate, cerLocalDate));

				return new ApiResponse(HttpStatus.OK, null, bean);
			} else {
				return new ApiResponse(HttpStatus.NO_CONTENT, "No data found", null);
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

	@Override
	public ApiResponse deleteUsers() {
		usersRepository.deleteAll();
		message = "Users deleted successfully";
		return new ApiResponse(HttpStatus.OK, message, null);
	}

}
