package com.tass.payment.repositories;

import com.tass.payment.entities.OrderPaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderPaymentRepository extends JpaRepository<OrderPaymentEntity , Long> {
}
