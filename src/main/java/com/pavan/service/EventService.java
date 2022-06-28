package com.pavan.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pavan.beans.ApiResponse;
import com.pavan.beans.EventBean;
import com.pavan.modal.User;

@Service
public interface EventService {

	public void saveEvent(EventBean bank, User currentUser) throws Exception;

	public ApiResponse getEvents(User user, String eventType);

	public ApiResponse getEvent(Long id);

	public ApiResponse deleteEvent(Long id);

	public ApiResponse deleteEvents(User currentUser);

	public void bulkUpload(List<List<Object>> data);

}
