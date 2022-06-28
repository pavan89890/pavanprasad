package com.pavan.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pavan.beans.ApiResponse;
import com.pavan.beans.TodoBean;
import com.pavan.modal.User;

@Service
public interface TodoService {

	public void saveTodo(User currentUser, TodoBean todo) throws Exception;

	public ApiResponse getTodos(User currentUser);

	public ApiResponse getTodo(Long id);

	public ApiResponse deleteTodo(Long id);

	public ApiResponse deleteTodos(User currentUser);

	public void bulkUpload(List<List<Object>> data);

}
