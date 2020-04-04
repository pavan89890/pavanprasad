package com.pavan.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.pavan.beans.ApiResponse;
import com.pavan.beans.EventBean;
import com.pavan.modal.Events;
import com.pavan.repository.EventRepository;
import com.pavan.service.EventService;
import com.pavan.util.DateUtil;
import com.pavan.util.Utility;

@Service
public class EventServiceImpl implements EventService {

	@Autowired
	EventRepository eventRepository;

	private String message = "";

	@Override
	public ApiResponse getEvents() {

		Map<String, Object> data = new LinkedHashMap<>();

		List<Events> events = eventRepository.getEventsOrderByDateDesc();
		List<EventBean> eventBeans = new ArrayList<EventBean>();

		for (Events event : events) {
			EventBean eventBean = toBean(event);
			eventBeans.add(eventBean);
		}

		if (Utility.isEmpty(events)) {
			return new ApiResponse(HttpStatus.NO_CONTENT, "No data found", null);
		}

		data.put("events", eventBeans);

		return new ApiResponse(HttpStatus.OK, null, data);
	}

	private EventBean toBean(Events event) {
		if (event != null) {
			EventBean bean = new EventBean();

			bean.setId(event.getId());
			bean.setEventType(event.getEventType());
			bean.setEventName(event.getEventName());
			bean.setEventDesc(event.getEventDesc());

			String eventDateStr = null;

			if (!Utility.isEmpty(event.getEventDate())) {
				eventDateStr = DateUtil.yyyy_MM_dd.format(event.getEventDate());
			}

			bean.setEventDateStr(eventDateStr);

			Date date1 = new Date();

			Date date2 = event.getEventDate();

			bean.setToday(DateUtil.isCurrentDateAndMonth(date1, date2));

			return bean;
		}
		return null;
	}

	@Override
	public void saveEvent(EventBean eventBean) throws Exception {

		if (!validData(eventBean)) {
			throw new Exception(message);
		}

		Events event = new Events();
		if (eventBean.getId() != null) {
			event.setId(eventBean.getId());
		}

		event.setEventType(eventBean.getEventType());
		event.setEventName(eventBean.getEventName());
		event.setEventDesc(eventBean.getEventDesc());

		Date eventDate = null;

		if (!Utility.isEmpty(eventBean.getEventDateStr())) {
			try {
				eventDate = DateUtil.yyyy_MM_dd.parse(eventBean.getEventDateStr());
			} catch (ParseException e) {
				message = e.getMessage();
				throw new Exception(message);
			}
		}

		event.setEventDate(eventDate);

		Events c = eventRepository.findByEventName(event.getEventName());

		if (c != null) {
			if ((event.getId() == null) || (event.getId() != c.getId())) {
				message = "Event Name Already Exists";
				throw new Exception(message);
			}
		}

		eventRepository.save(event);

	}

	private boolean validData(EventBean bean) {

		if (Utility.isEmpty(bean.getEventType())) {
			message = "Please Enter Event Type";
			return false;
		}
		
		if (Utility.isEmpty(bean.getEventName())) {
			message = "Please Enter Event Name";
			return false;
		}
		
		if (Utility.isEmpty(bean.getEventDesc())) {
			message = "Please Enter Event Description";
			return false;
		}
		
		if (Utility.isEmpty(bean.getEventDateStr())) {
			message = "Please Enter Event Date";
			return false;
		}

		return true;
	}

	@Override
	public ApiResponse getEvent(Long id) {
		if (id == null || id == 0) {
			return new ApiResponse(HttpStatus.NO_CONTENT, "No data found", null);
		} else {

			Optional<Events> eventOp = eventRepository.findById(id);
			if (eventOp.isPresent()) {
				Events event = eventOp.get();
				EventBean eventBean = toBean(event);
				return new ApiResponse(HttpStatus.OK, null, eventBean);
			} else {
				return new ApiResponse(HttpStatus.NO_CONTENT, "No data found", null);
			}
		}
	}

	@Override
	public ApiResponse deleteEvent(Long id) {
		if (getEvent(id).getData() == null) {
			return new ApiResponse(HttpStatus.NO_CONTENT, "No data found", null);
		} else {
			eventRepository.deleteById(id);
			message = "Event deleted successfully";
			return new ApiResponse(HttpStatus.OK, message, null);
		}
	}

	@Override
	public ApiResponse deleteEvents() {
		eventRepository.deleteAll();
		message = "Events deleted successfully";
		return new ApiResponse(HttpStatus.OK, message, null);
	}

}
