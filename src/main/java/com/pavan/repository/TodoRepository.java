package com.pavan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pavan.modal.Todo;
import com.pavan.modal.User;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
	public Todo findByUserAndTodo(User currentUser, String todo);

	@Query(value = "from Todo t where t.user=:user order by priority,todo")
	public List<Todo> getTodosOrderByTodo(User user);

	@Modifying
	@Query("delete from Todo t where t.user=:user")
	public void deleteByUser(@Param("user") User user);

}
