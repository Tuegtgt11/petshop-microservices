package com.tass.order.command;

import com.tass.common.model.ApplicationException;
import com.tass.common.model.BaseResponse;
import com.tass.common.model.payment.request.OrderPaymentRequest;
import com.tass.common.model.payment.response.OrderPaymentResponse;
import com.tass.order.connector.PaymentConnector;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class PaymentCommand {

    @Autowired
    PaymentConnector paymentConnector;

    public OrderPaymentResponse createdOrderPayment(long orderId, long userId, long amount, double requireBalance) throws ApplicationException {

        OrderPaymentRequest request = new OrderPaymentRequest();
        request.setOrderId(orderId);
        request.setUserId(userId);
        request.setAmount(amount);
        request.setRequireBalance(requireBalance);

        return paymentConnector.createTransaction(request);
    }

    public BaseResponse rollback(long transactionId) {
        return paymentConnector.rollback(transactionId);
    }
}
