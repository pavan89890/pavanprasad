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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pavan.beans.ApiResponse;
import com.pavan.beans.EventBean;
import com.pavan.modal.User;
import com.pavan.service.EventService;
import com.pavan.service.LoginService;
import com.pavan.util.Utility;

@RestController
@RequestMapping(path = "/api/events")
@CrossOrigin("*")
public class EventController {

	@Autowired
	private EventService eventService;

	@Autowired
	private LoginService loginService;

	private String message;

	@PostMapping
	@Transactional(rollbackOn = Exception.class)
	public ApiResponse saveEvent(@RequestHeader("userToken") String userToken,
			@RequestBody(required = true) EventBean eventBean) {

		try {
			User currentUser = loginService.getUserFromToken(userToken);
			if (currentUser != null) {
				eventService.saveEvent(eventBean, currentUser);

				if (Utility.isEmpty(eventBean.getId())) {
					message = "Event saved successfully";
				} else {
					message = "Event updated successfully";
				}

				return new ApiResponse(HttpStatus.OK, message, null);
			} else {
				return new ApiResponse(HttpStatus.UNAUTHORIZED, "Unauthorized to acccess this resource", null);
			}
		} catch (Exception e) {
			message = "Error-" + e.getMessage();
			return new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, message, null);
		}

	}

	@GetMapping
	public ApiResponse getEvents(@RequestHeader("userToken") String userToken,
			@RequestParam(value = "eventType", required = false) String eventType) {
		User currentUser = loginService.getUserFromToken(userToken);
		if (currentUser != null) {
			return eventService.getEvents(currentUser,eventType);
		} else {
			return new ApiResponse(HttpStatus.UNAUTHORIZED, "Unauthorized to acccess this resource", null);
		}
	}

	@GetMapping("/{id}")
	public ApiResponse getEvent(@RequestHeader("userToken") String userToken, @PathVariable(value = "id") Long id) {

		User currentUser = loginService.getUserFromToken(userToken);
		if (currentUser != null) {
			return eventService.getEvent(id);
		} else {
			return new ApiResponse(HttpStatus.UNAUTHORIZED, "Unauthorized to acccess this resource", null);
		}

	}

	@DeleteMapping("/{id}")
	public ApiResponse deleteEvent(@RequestHeader("userToken") String userToken, @PathVariable(value = "id") Long id) {
		User currentUser = loginService.getUserFromToken(userToken);
		if (currentUser != null) {
			return eventService.deleteEvent(id);
		} else {
			return new ApiResponse(HttpStatus.UNAUTHORIZED, "Unauthorized to acccess this resource", null);
		}
	}

	@DeleteMapping
	public ApiResponse deleteUserEvents(@RequestHeader("userToken") String userToken) {
		User currentUser = loginService.getUserFromToken(userToken);
		if (currentUser != null) {
			return eventService.deleteEvents(currentUser);
		} else {
			return new ApiResponse(HttpStatus.UNAUTHORIZED, "Unauthorized to acccess this resource", null);
		}
	}

	@DeleteMapping("/all")
	public ApiResponse deleteEvents(@RequestHeader("userToken") String userToken) {
		User currentUser = loginService.getUserFromToken(userToken);
		if (currentUser != null) {
			return eventService.deleteEvents(null);
		} else {
			return new ApiResponse(HttpStatus.UNAUTHORIZED, "Unauthorized to acccess this resource", null);
		}

	}
}
