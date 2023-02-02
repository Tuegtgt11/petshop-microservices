package com.tass.payment.entities;

import com.tass.common.model.payment.request.OrderPaymentRequest;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "order_payment")
public class OrderPaymentEntity {

    @Id
    private long orderId;
    private long amount;
    private int status;

    public OrderPaymentEntity() {

    }

    public OrderPaymentEntity(OrderPaymentRequest request) {
        this.setStatus(1);
        this.setOrderId(request.getOrderId());
        this.setAmount(request.getAmount());
    }
}
