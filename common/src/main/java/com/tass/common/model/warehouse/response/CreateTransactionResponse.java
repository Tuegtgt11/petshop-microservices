package com.tass.common.model.warehouse.response;

import com.tass.common.model.BaseResponse;
import com.tass.common.model.warehouse.dto.CreatedTransactionData;
import lombok.Data;

@Data
public class CreateTransactionResponse extends BaseResponse {
    private CreatedTransactionData data;
}
