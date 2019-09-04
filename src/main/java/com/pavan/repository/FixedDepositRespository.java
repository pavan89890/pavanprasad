package com.pavan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pavan.modal.FixedDeposit;

@Repository
public interface FixedDepositRespository extends JpaRepository<FixedDeposit, Long> {
}
