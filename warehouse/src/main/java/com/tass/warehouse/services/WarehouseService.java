package com.tass.warehouse.services;


import com.tass.common.model.ApplicationException;
import com.tass.common.model.BaseResponse;
import com.tass.common.model.ERROR;
import com.tass.common.model.warehouse.dto.CreatedTransactionData;
import com.tass.common.model.warehouse.request.CreateTransactionRequest;
import com.tass.common.model.warehouse.request.RollbackTransactionRequest;
import com.tass.common.model.warehouse.response.CreateTransactionResponse;
import com.tass.warehouse.domain.ProductWarehouseDomain;
import com.tass.warehouse.entities.OrderTransaction;
import com.tass.warehouse.entities.ProductWarehouse;
import com.tass.warehouse.repository.OrderTransactionRepository;
import com.tass.warehouse.repository.ProductWarehouseRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;

@Service
@Log4j2
public class WarehouseService {

    @Autowired
    ProductWarehouseRepository productWarehouseRepository;

    @Autowired
    OrderTransactionRepository orderTransactionRepository;

    @Autowired
    ProductWarehouseDomain productWarehouseDomain;

    @Transactional
    public CreateTransactionResponse createTransactionResponse(CreateTransactionRequest request) throws ApplicationException {

        log.info("created warehouse product transaction- {}", request);

        this.validateRequest(request);

        ProductWarehouse productWarehouse = productWarehouseDomain.getProductWarehouseInfoByProductId(request.getProductId());

        productWarehouseDomain.validateWarehouseTotal(productWarehouse, request.getTotal());

        productWarehouse.setTotal(productWarehouse.getTotal() - request.getTotal());
        productWarehouse.setTotalSell(productWarehouse.getTotalSell() + request.getTotal());

        productWarehouseRepository.save(productWarehouse);

        OrderTransaction orderTransaction = new OrderTransaction(request);

        orderTransactionRepository.save(orderTransaction);

        CreateTransactionResponse response = new CreateTransactionResponse();

        CreatedTransactionData data = new CreatedTransactionData();
        data.setTransactionId(orderTransaction.getId());

        response.setData(data);
        log.info("created warehouse product transaction DONE");

        return response;
    }

    @Transactional
    public BaseResponse rollbackTransaction(RollbackTransactionRequest request) throws ApplicationException{

        validateRollbackRequest(request);

        Optional<OrderTransaction> orderTransactionOpt = orderTransactionRepository.findById(request.getTransactionId());

        if (orderTransactionOpt.isEmpty()){
            throw new ApplicationException(ERROR.WARE_HOUSE_ORDER_TRANSACTION_INVALID);
        }

        OrderTransaction orderTransaction = orderTransactionOpt.get();

        orderTransaction.setStatus(2);
        orderTransactionRepository.save(orderTransaction);

        ProductWarehouse productWarehouse = productWarehouseDomain.getProductWarehouseInfoByProductId(orderTransaction.getProductId());

        productWarehouse.setTotal(productWarehouse.getTotal() + orderTransaction.getTotal());

        productWarehouseRepository.save(productWarehouse);

        return new BaseResponse();
    }

    private void validateRequest(CreateTransactionRequest request) {
        if (request.getTotal() < 1) {
            log.debug("ware house product transaction fail - total invalid");
            throw new ApplicationException(ERROR.INVALID_PARAM, "total param is invalid");
        }

        if (request.getProductId() < 1) {
            log.debug("ware house product transaction fail - productId invalid");
            throw new ApplicationException(ERROR.INVALID_PARAM, "param productId is invalid");
        }
    }

    private void validateRollbackRequest(RollbackTransactionRequest request) {
        if (request.getOrderId() < 1) {
            log.debug("ware house product rollback transaction fail - orderId invalid");
            throw new ApplicationException(ERROR.INVALID_PARAM, "order id is invalid");
        }

        if (request.getTransactionId() < 1) {
            log.debug("ware house product transaction fail - orderId invalid");
            throw new ApplicationException(ERROR.INVALID_PARAM, "param orderId is invalid");
        }
    }
}
