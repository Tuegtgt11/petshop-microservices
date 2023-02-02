package com.tass.warehouse.repository;

import com.tass.warehouse.entities.OrderTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderTransactionRepository extends JpaRepository<OrderTransaction, Long> {
}
