package com.pavan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.pavan.modal.Bank;
import com.pavan.modal.User;

@Repository
public interface BankRespository extends JpaRepository<Bank, Long> {
	public Bank findByUserAndName(User currentUser,String name);

	@Query(value = "from Bank b where b.user=:user order by balance desc")
	List<Bank> getBanksOrderByBalDesc(User user);

	@Query(value = "select sum(balance) from Bank where user=:user")
	public Float getTotalBalance(User user);
	
	@Query(value = "select sum(balance) from Bank")
	public Float getTotalBalance();

	public Bank findByUserAndId(User currentUser, Long id);
}
