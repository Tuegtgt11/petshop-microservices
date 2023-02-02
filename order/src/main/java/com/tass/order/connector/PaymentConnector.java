package com.tass.order.connector;

import com.tass.common.model.BaseResponse;
import com.tass.common.model.payment.request.OrderPaymentRequest;
import com.tass.common.model.payment.response.OrderPaymentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "payment-service" , url ="${tass.payment.address}")
public interface PaymentConnector {

    @PostMapping("/create")
    OrderPaymentResponse createTransaction(@RequestBody OrderPaymentRequest request);

    @PutMapping("rollback/{transId}")
    BaseResponse rollback(@PathVariable(name = "transId") long transactionId);
}
