package com.pavan.service;

import org.springframework.stereotype.Service;

import com.pavan.beans.ApiResponse;
import com.pavan.beans.EventBean;

@Service
public interface EventService {

	public void saveEvent(EventBean bank) throws Exception;

	public ApiResponse getEvents();

	public ApiResponse getEvent(Long id);

	public ApiResponse deleteEvent(Long id);

	public ApiResponse deleteEvents();

}
