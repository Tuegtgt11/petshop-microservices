package com.tass.order.services;

import com.tass.common.model.ApplicationException;
import com.tass.common.model.BaseResponse;
import com.tass.common.model.BaseResponseV2;
import com.tass.common.model.ERROR;
import com.tass.common.model.constans.ORDER;
import com.tass.common.model.constans.PAYMENT;
import com.tass.common.model.constans.WAREHOUSE;
import com.tass.common.model.dto.product.ProductDTO;
import com.tass.common.model.payment.response.OrderPaymentResponse;
import com.tass.common.model.userauthen.UserDTO;
import com.tass.common.model.warehouse.response.CreateTransactionResponse;
import com.tass.common.myenums.ProductStatus;
import com.tass.order.command.PaymentCommand;
import com.tass.order.command.WareHouseCommand;
import com.tass.order.connector.ProductConnector;
import com.tass.order.entities.Order;
import com.tass.order.repositories.OrderRepository;
import com.tass.order.request.CreatedOrderRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
public class OrderService extends BaseService{
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductConnector productConnector;

    @Autowired
    WareHouseCommand wareHouseCommand;

    @Autowired
    PaymentCommand paymentCommand;

    public BaseResponse createdOrder(CreatedOrderRequest request) throws ApplicationException {
        log.info("created order with request {}", request);

        BaseResponse response = new BaseResponse();

        UserDTO userDTO = getUserDTO();

        if (request.getProductId() < 1 || request.getTotal() < 1) {
            throw new ApplicationException(ERROR.INVALID_PARAM);
        }

        BaseResponseV2<ProductDTO> productInfoResponse = productConnector.getProductById(request.getProductId());

        if (!productInfoResponse.isSuccess()) {
            throw new ApplicationException(ERROR.INVALID_PARAM);
        }

        ProductDTO productDTO = productInfoResponse.getData();

        if (productDTO == null) {
            throw new ApplicationException(ERROR.INVALID_PARAM);
        }

        Order order = new Order();

        order.setIsSuccess(ORDER.SUCCESS_STATUS.FAIL);
        order.setUserId(userDTO.getUserId());
        order.setStatus(ORDER.STATUS.CREATED);
        order.setProductId(request.getProductId());
        order.setTotalItems(request.getTotal());
        order.setTotalPrice(order.getTotalItems() * productDTO.getPrice());

        orderRepository.saveAndFlush(order);

        CreateTransactionResponse createTransactionResponse = wareHouseCommand.createdWarehouseTransaction(request.getProductId(), request.getTotal(), order.getId());

        if (!createTransactionResponse.isSuccess()) {
            log.debug("error {}", createTransactionResponse);

            order.setStatus(ORDER.STATUS.FAIL);
            order.setErrorCode("");
            order.setPaymentStatus(0);
            order.setWarehouseStatus(WAREHOUSE.STATUS.FAIL);

            orderRepository.saveAndFlush(order);

            response.setMessage(createTransactionResponse.getMessage());
            response.setCode(createTransactionResponse.getCode());
            response.setErrorCode(createTransactionResponse.getErrorCode());

            return response;
        }

        order.setWarehouseStatus(WAREHOUSE.STATUS.SUCCESS);

        order.setWarehouseTransId(createTransactionResponse.getData().getTransactionId());

        order.setStatus(ORDER.STATUS.SUCCESS);

        orderRepository.saveAndFlush(order);

        OrderPaymentResponse paymentResponse = paymentCommand.createdOrderPayment(order.getId(), order.getUserId(), order.getTotalItems(), order.getTotalPrice());

        if (!paymentResponse.isSuccess()) {
            wareHouseCommand.rollbackTransaction(order.getWarehouseTransId(), order.getId());

            order.setWarehouseStatus(WAREHOUSE.STATUS.ROLLBACK);
            order.setWarehouseTransId(createTransactionResponse.getData().getTransactionId());

            order.setPaymentStatus(PAYMENT.STATUS.FAIL);

            order.setStatus(ORDER.STATUS.FAIL);

            orderRepository.saveAndFlush(order);

            response.setMessage(paymentResponse.getMessage());
            response.setCode(paymentResponse.getCode());
            response.setErrorCode(paymentResponse.getErrorCode());

            return response;
        }

        order.setPaymentStatus(PAYMENT.STATUS.SUCCESS);
        order.setPaymentTransId(paymentResponse.getData().getTransactionId());
        order.setStatus(ORDER.STATUS.SUCCESS);
        orderRepository.saveAndFlush(order);

        return new BaseResponse();
    }
}
