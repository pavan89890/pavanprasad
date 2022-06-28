package com.pavan.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.pavan.beans.ApiResponse;
import com.pavan.beans.TodoBean;
import com.pavan.modal.Todo;
import com.pavan.modal.User;
import com.pavan.repository.TodoRepository;
import com.pavan.repository.UserRespository;
import com.pavan.service.TodoService;
import com.pavan.util.Utility;

@Service
public class TodoServiceImpl implements TodoService {

	@Autowired
	TodoRepository todoRepository;

	@Autowired
	UserRespository userRepository;

	private String message = "";

	@Override
	public ApiResponse getTodos(User currentUser) {

		Map<String, Object> data = new LinkedHashMap<>();

		List<Todo> todos = todoRepository.getTodosOrderByTodo(currentUser);

		if (Utility.isEmpty(todos)) {
			return new ApiResponse(HttpStatus.NO_CONTENT, "No data found", null);
		}

		data.put("todos", todos);

		return new ApiResponse(HttpStatus.OK, null, data);
	}

	@Override
	public void saveTodo(User currentUser, TodoBean todoBean) throws Exception {

		if (!validData(todoBean)) {
			throw new Exception(message);
		}

		Todo todo = new Todo();
		if (todoBean.getId() != null) {
			todo.setId(todoBean.getId());
		}
		todo.setTodo(todoBean.getTodo());
		todo.setPriority(todoBean.getPriority());
		todo.setStatus(todoBean.isStatus());

		todo.setUser(currentUser);

		Todo c = todoRepository.findByUserAndTodo(currentUser, todo.getTodo());

		if (c != null) {
			if ((todo.getId() == null) || (todo.getId().longValue() != c.getId().longValue())) {
				message = "Todo Already Exists";
				throw new Exception(message);
			}
		}

		todoRepository.save(todo);

	}

	private boolean validData(TodoBean bean) {

		if (Utility.isEmpty(bean.getTodo())) {
			message = "Please Enter To Do";
			return false;
		}

		if (bean.getPriority() == null) {
			message = "Please Select Priority";
			return false;
		}

		return true;
	}

	@Override
	public ApiResponse getTodo(Long id) {
		if (id == null || id == 0) {
			return new ApiResponse(HttpStatus.NO_CONTENT, "No data found", null);
		} else {

			Todo todo = todoRepository.getOne(id);
			if (todo != null) {
				return new ApiResponse(HttpStatus.OK, null, todo);
			} else {
				return new ApiResponse(HttpStatus.NO_CONTENT, "No data found", null);
			}
		}
	}

	@Override
	public ApiResponse deleteTodo(Long id) {
		if (getTodo(id).getData() == null) {
			return new ApiResponse(HttpStatus.NO_CONTENT, "No data found", null);
		} else {
			todoRepository.deleteById(id);
			message = "Todo deleted successfully";
			return new ApiResponse(HttpStatus.OK, message, null);
		}
	}

	@Override
	public ApiResponse deleteTodos(User currentUser) {
		if (currentUser != null) {
			todoRepository.deleteByUser(currentUser);
			message = "Hi " + currentUser.getName() + ",all your todos deleted successfully";
		} else {
			todoRepository.deleteAll();
			message = "Todos deleted successfully";
		}
		return new ApiResponse(HttpStatus.OK, message, null);
	}

	@Override
	public void bulkUpload(List<List<Object>> data) {
		List<Todo> todos = new ArrayList<>();

		for (List<Object> rowData : data.subList(1, data.size())) {
			Todo todo = new Todo();

			todo.setUser(userRepository.getOne(Double.valueOf(rowData.get(1) + "").longValue()));
			todo.setTodo((String) rowData.get(2));
			todo.setPriority(Double.valueOf(rowData.get(3) + "").intValue());

			if(rowData.get(4)!=null && (rowData.get(4) instanceof Boolean)) {
				todo.setStatus((Boolean) rowData.get(4));
			}else {
				todo.setStatus(false);
			}

			todos.add(todo);
		}

		todoRepository.deleteAll();
		todoRepository.saveAll(todos);
	}
}
