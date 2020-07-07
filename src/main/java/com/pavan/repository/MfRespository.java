package com.pavan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pavan.modal.MutualFund;
import com.pavan.modal.User;

@Repository
public interface MfRespository extends JpaRepository<MutualFund, Long> {

	@Query(value = "select sum(investedAmount) from MutualFund where user=:user")
	public Float getTotalInvested(@Param("user") User user);

	@Query(value = "select sum(currentAmount) from MutualFund where user=:user")
	public Float getTotalCurrent(@Param("user") User user);

	public List<MutualFund> findByUserOrderByDepositedOn(User user);

	@Modifying
	@Query("delete from MutualFund f where f.user=:user")
	public void deleteByUser(@Param("user") User user);
}
