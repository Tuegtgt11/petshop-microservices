package com.tass.payment.controllers;

import com.tass.common.model.ApplicationException;
import com.tass.common.model.BaseResponse;
import com.tass.common.model.payment.request.OrderPaymentRequest;
import com.tass.common.model.payment.request.RollBackPaymentRequest;
import com.tass.common.model.payment.response.OrderPaymentResponse;
import com.tass.payment.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction")
public class PaymentController {

    @Autowired
    PaymentService paymentService;

    @PostMapping("/create")
    public OrderPaymentResponse createTransaction(@RequestBody OrderPaymentRequest request) throws
            ApplicationException{

        return paymentService.createOrderPayment(request);
    }

    @PutMapping("rollback")
    public BaseResponse rollbackTransaction(@RequestBody RollBackPaymentRequest request) throws
            ApplicationException{

        return paymentService.rollbackOrderPayment(request);
    }


}
