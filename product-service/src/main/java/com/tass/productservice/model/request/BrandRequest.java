package com.tass.productservice.model.request;

import com.tass.common.myenums.BrandStatus;
import lombok.Data;

import javax.persistence.Column;

@Data
public class BrandRequest {
    private String name;
    @Column(columnDefinition = "text")
    private String image;
    private BrandStatus status;
}
