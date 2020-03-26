package com.pavan.rest.controller;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pavan.beans.ApiResponse;
import com.pavan.beans.EventBean;
import com.pavan.service.EventService;
import com.pavan.util.Utility;

@RestController
@RequestMapping(path = "/api/events")
@CrossOrigin("*")
public class EventController {

	@Autowired
	private EventService eventService;

	private String message;

	@PostMapping
	@Transactional(rollbackOn = Exception.class)
	public ApiResponse saveEvent(@RequestBody(required = true) EventBean eventBean) {

		try {
			eventService.saveEvent(eventBean);

			if (Utility.isEmpty(eventBean.getId())) {
				message = "Event saved successfully";
			} else {
				message = "Event updated successfully";
			}

			return new ApiResponse(HttpStatus.OK, message, null);
		} catch (Exception e) {
			message = "Error-" + e.getMessage();
			return new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, message, null);
		}

	}

	@GetMapping
	public ApiResponse events() {
		return eventService.getEvents();
	}

	@GetMapping("/{id}")
	public ApiResponse getEvent(@PathVariable(value = "id") Long id) {
		return eventService.getEvent(id);
	}

	@DeleteMapping("/{id}")
	public ApiResponse deleteEvent(@PathVariable(value = "id") Long id) {
		return eventService.deleteEvent(id);
	}

	@DeleteMapping
	public ApiResponse deleteEvents() {
		return eventService.deleteEvents();
	}
}
