package com.tass.order.controllers;

import com.tass.common.customanotation.RequireUserLogin;
import com.tass.common.model.ApplicationException;
import com.tass.common.model.BaseResponse;
import com.tass.order.request.CreatedOrderRequest;
import com.tass.order.services.BaseService;
import com.tass.order.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/order")
public class OrderController extends BaseService {

    @Autowired
    OrderService orderService ;

    @PostMapping
    public BaseResponse createdOrder(@RequestBody CreatedOrderRequest request) throws
            ApplicationException {
        return orderService.createdOrder(request);
    }
}
