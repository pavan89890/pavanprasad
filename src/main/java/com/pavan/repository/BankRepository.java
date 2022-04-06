package com.pavan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pavan.modal.Bank;
import com.pavan.modal.User;

@Repository
public interface BankRepository extends JpaRepository<Bank, Long> {
	public Bank findByUserAndName(User currentUser, String name);

	@Query(value = "from Bank b where b.user=:user order by balance desc")
	public List<Bank> getBanksOrderByBalDesc(User user);

	@Query(value = "select sum(balance) from Bank where user=:user")
	public Float getTotalBalance(@Param("user") User user);
	
	@Query(value = "select sum(balance) from Bank where user=:user and name<>'Khazana scheme' and name<>'PPF Sbi'")
	public Float getBankBalanceWithoutPPF(@Param("user") User user);

	@Modifying
	@Query("delete from Bank b where b.user=:user")
	public void deleteByUser(@Param("user") User user);

}
