package com.tass.productservice.model.request;

import com.tass.common.myenums.ProductStatus;
import com.tass.productservice.entities.Brand;
import com.tass.productservice.entities.Category;
import com.tass.productservice.entities.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    private String name;
    @Column(columnDefinition = "text")
    private String description;
    private String barcode;
    private String images;
    @Column(columnDefinition = "text")
    private String detail;
    private int sold;
    private Size size;
    private BigDecimal price;
    private Brand brand;
    private Category category;
    private ProductStatus status;
}
