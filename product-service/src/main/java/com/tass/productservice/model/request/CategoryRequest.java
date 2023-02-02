package com.tass.productservice.model.request;

import com.tass.common.myenums.CategoryStatus;
import lombok.Data;

import javax.persistence.Column;

@Data
public class CategoryRequest {
    private String name;
    @Column(columnDefinition = "text")
    private String icon;
    private CategoryStatus status;
}
