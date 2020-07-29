package com.pavan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pavan.modal.User;

@Repository
public interface UserRespository extends JpaRepository<User, Long> {
	public User findByMobile(String mobile);

	public User findByEmailAndPassword(String username, String password);

	@Query(value = "from User u where (u.email=:username or u.mobile=:username) and u.password=:password")
	public User findByEmailOrMobileAndPassword(@Param("username") String username, @Param("password") String password);
}
