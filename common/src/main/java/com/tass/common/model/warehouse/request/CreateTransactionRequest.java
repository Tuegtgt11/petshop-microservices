package com.tass.common.model.warehouse.request;

import lombok.Data;

@Data
public class CreateTransactionRequest {
    private long productId;
    private int total;
    private long orderId;
}
