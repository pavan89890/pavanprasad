package com.pavan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pavan.modal.Chit;
import com.pavan.modal.User;

@Repository
public interface ChitRepository extends JpaRepository<Chit, Long> {

	public Chit findByUserAndMonthAndYear(User currentUser, Integer month, Integer year);

	@Query(value = "from Chit c where c.user=:user order by year desc,month desc")
	public List<Chit> getUserChitsOrderByYearDesc(@Param("user") User currentUser);

	@Query(value = "select sum(actualAmount) from Chit c where c.user=:user")
	public Float getTotalDeposited(@Param("user") User currentUser);

	@Modifying
	@Query("delete from Chit c where c.user=:user")
	public void deleteByUser(@Param("user") User user);
}
