package com.tass.warehouse.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "product_warehouse")
public class ProductWarehouse {

    @Id
    private long productId;

    private long total;

    private long totalSell;
}
