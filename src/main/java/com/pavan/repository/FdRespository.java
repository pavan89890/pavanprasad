package com.pavan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pavan.modal.Fd;
import com.pavan.modal.User;

@Repository
public interface FdRespository extends JpaRepository<Fd, Long> {

	@Query(value = "select sum(depAmount) from Fd where user=:user")
	public Float getTotalDeposited(@Param("user") User user);

	@Query(value = "select sum(maturedAmount) from Fd where user=:user")
	public Float getTotalMatured(@Param("user") User user);

	public List<Fd> findByUserOrderByMaturedOn(User user);

	@Modifying
	@Query("delete from Fd f where f.user=:user")
	public void deleteByUser(@Param("user") User user);
}
