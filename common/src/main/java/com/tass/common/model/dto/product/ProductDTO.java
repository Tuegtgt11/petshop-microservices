package com.tass.common.model.dto.product;

import com.tass.common.myenums.ProductStatus;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDTO {
    private long id;
    private String name;

    private double price;
    private ProductStatus status;
}
