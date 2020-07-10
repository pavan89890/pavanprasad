package com.pavan.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.pavan.beans.ApiResponse;
import com.pavan.beans.ChitBean;
import com.pavan.modal.Chit;
import com.pavan.modal.User;
import com.pavan.repository.ChitRepository;
import com.pavan.service.ChitService;
import com.pavan.util.DateUtil;
import com.pavan.util.Utility;

@Service
public class ChitServiceImpl implements ChitService {

	@Autowired
	ChitRepository chitRepository;

	private String message = "";

	@Override
	public ApiResponse getChits(User currentUser) {
		Map<String, Object> data = new LinkedHashMap<>();

		List<Chit> chits = chitRepository.getUserChitsOrderByYearDesc(currentUser);
		List<ChitBean> chitBeans = new ArrayList<>();

		Float totalDeposited = 0f;
		Float totalProfit = 0f;

		if (Utility.isEmpty(chits)) {
			return new ApiResponse(HttpStatus.NO_CONTENT, "No data found", null);
		} else {

			for (Chit chit : chits) {
				ChitBean bean = toBean(chit);
				totalDeposited += bean.getActualAmount();
				totalProfit += bean.getProfit();
				chitBeans.add(bean);
			}
		}

		data.put("chits", chitBeans);

		data.put("totalDeposited", Utility.formatNumber(totalDeposited));
		data.put("totalMatured", Utility.formatNumber(3 * 12 * 6000f));
		data.put("totalProfit", Utility.formatNumber(totalProfit));

		return new ApiResponse(HttpStatus.OK, null, data);
	}

	private ChitBean toBean(Chit chit) {
		ChitBean bean = new ChitBean();
		bean.setId(chit.getId());
		bean.setMonth(chit.getMonth());
		bean.setMonthStr(DateUtil.getMonthName(chit.getMonth()));
		bean.setYear(chit.getYear());
		bean.setActualAmount(Utility.formatNumber(chit.getActualAmount()));
		bean.setPaidAmount(Utility.formatNumber(chit.getPaidAmount()));
		bean.setProfit(Utility.formatNumber(chit.getProfit()));
		return bean;
	}

	@Override
	public void saveChit(User currentUser, ChitBean chitBean) throws Exception {

		if (!validData(chitBean)) {
			throw new Exception(message);
		}

		Chit chit = new Chit();
		if (chitBean.getId() != null) {
			chit.setId(chitBean.getId());
		}
		chit.setMonth(chitBean.getMonth());
		chit.setYear(chitBean.getYear());
		chit.setActualAmount(Utility.formatNumber(chitBean.getActualAmount()));
		chit.setPaidAmount(Utility.formatNumber(chitBean.getPaidAmount()));
		chit.setProfit(Utility.formatNumber(chit.getActualAmount() - chitBean.getPaidAmount()));
		chit.setUser(currentUser);

		Chit c = null;
		try {
			c = chitRepository.findByUserAndMonthAndYear(currentUser, chitBean.getMonth(), chitBean.getYear());
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (c != null) {
			if ((chitBean.getId() == null) || (chitBean.getId().longValue() != c.getId().longValue())) {
				message = "Chit For Month and Year combination Already Exists";
				throw new Exception(message);
			}
		}

		chitRepository.save(chit);

	}

	private boolean validData(ChitBean bean) {

		if (Utility.isEmpty(bean.getMonth())) {
			message = "Please Select Month";
			return false;
		}

		if (Utility.isEmpty(bean.getYear())) {
			message = "Please Select Year";
			return false;
		}

		if (bean.getActualAmount() == null) {
			message = "Please Enter Actual Amount";
			return false;
		}

		if (bean.getPaidAmount() == null) {
			message = "Please Enter Paid Amount";
			return false;
		}

		return true;
	}

	@Override
	public ApiResponse getChit(Long id) {
		if (id == null || id == 0) {
			return new ApiResponse(HttpStatus.NO_CONTENT, "No data found", null);
		} else {

			Optional<Chit> chitOp = chitRepository.findById(id);
			if (chitOp.isPresent()) {
				ChitBean chit = toBean(chitOp.get());
				return new ApiResponse(HttpStatus.OK, null, chit);
			} else {
				return new ApiResponse(HttpStatus.NO_CONTENT, "No data found", null);
			}
		}
	}

	@Override
	public ApiResponse deleteChit(Long id) {
		if (getChit(id).getData() == null) {
			return new ApiResponse(HttpStatus.NO_CONTENT, "No data found", null);
		} else {
			chitRepository.deleteById(id);
			message = "Chit deleted successfully";
			return new ApiResponse(HttpStatus.OK, message, null);
		}
	}

	@Override
	public ApiResponse deleteChits(User currentUser) {
		if (currentUser != null) {
			chitRepository.deleteByUser(currentUser);
			message = "Hi " + currentUser.getName() + ",all your chits deleted successfully";
		} else {
			chitRepository.deleteAll();
			message = "Chits deleted successfully";
		}
		return new ApiResponse(HttpStatus.OK, message, null);

	}

}
