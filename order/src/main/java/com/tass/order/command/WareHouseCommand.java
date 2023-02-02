package com.tass.order.command;

import com.tass.common.model.ApplicationException;
import com.tass.common.model.BaseResponse;
import com.tass.common.model.warehouse.request.CreateTransactionRequest;
import com.tass.common.model.warehouse.request.RollbackTransactionRequest;
import com.tass.common.model.warehouse.response.CreateTransactionResponse;
import com.tass.order.connector.ProductWareHouseConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WareHouseCommand {

    @Autowired
    ProductWareHouseConnector productWareHouseConnector;


    public CreateTransactionResponse createdWarehouseTransaction(long productId , int total, long orderId) throws
        ApplicationException{

        CreateTransactionRequest request = new CreateTransactionRequest();
        request.setProductId(productId);
        request.setTotal(total);
        request.setOrderId(orderId);


        return productWareHouseConnector.createTransaction(request);

    }

    public BaseResponse rollbackTransaction(long transactionId , long orderId){

        RollbackTransactionRequest request = new RollbackTransactionRequest();
        request.setOrderId(orderId);
        request.setTransactionId(transactionId);

        return productWareHouseConnector.rollbackTransaction(request);
    }
}
