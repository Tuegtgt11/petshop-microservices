package com.tass.warehouse.controllers;


import com.tass.common.model.ApplicationException;
import com.tass.common.model.BaseResponse;
import com.tass.common.model.warehouse.request.CreateTransactionRequest;
import com.tass.common.model.warehouse.request.RollbackTransactionRequest;
import com.tass.common.model.warehouse.response.CreateTransactionResponse;
import com.tass.warehouse.services.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/transaction")
public class ProductWarehouseController {

    @Autowired
    WarehouseService warehouseService;

    @PostMapping("/create")
    public CreateTransactionResponse createTransaction(@RequestBody CreateTransactionRequest request) throws
            ApplicationException {

        return warehouseService.createTransactionResponse(request);
    }

    @PutMapping("/rollback")
    public BaseResponse createTransaction(@RequestBody RollbackTransactionRequest request) throws
            ApplicationException {
        return warehouseService.rollbackTransaction(request);

    }
}
